package crawler;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Vadzimko
 */
public class OfflineWebCrawler implements WebCrawler {
    private Map<String, Page> pages = new HashMap<>();
    private Map<String, Image> images = new HashMap<>();
    private Map<String, Style> styles = new HashMap<>();
    private Map<String, Script> scripts = new HashMap<>();
    private BufferedReader input;
    private int ch;
    private Queue<String> nextQueue = new LinkedList<>();
    private String directory;
    private StringBuilder content;

    public void crawl(String url, int depth) {
        directory = System.getProperty("user.dir") + "\\root\\";
        System.out.println(directory);
        Queue<String> currQueue = new LinkedList<>();
        currQueue.add(url);

        while (depth > 0) {
            for (String s : currQueue) {
                if (!pages.containsKey(s) || !pages.get(s).isVisited()) {
                    crawlPage(s);
                }
            }
            currQueue = nextQueue;
            nextQueue = new LinkedList<>();
            depth--;
        }
    }

    private void crawlPage(String link) {
        try {
            content = new StringBuilder();
            Page page;
            if (pages.containsKey(link)) {
                page = pages.get(link);
            } else {
                page = new Page(directory, new URL(link));
                pages.put(link, page);
            }

            try {
                input = new BufferedReader(new InputStreamReader(page.getUrl().openConnection().getInputStream(), "utf-8"));
            } catch (IOException e) {
                page.makeVisited();
                return;
            }

            while (true) {
                if (!findBeginOfTag()) {
                    break;
                }
                String tag = readOpenTag().toLowerCase();

                switch (tag) {
                    case "!--": {
                        skipComment();
                        break;
                    }
                    case "a": {
                        findLink(page);
                        break;
                    }
                    case "img": {
                        findImage(page);
                        break;
                    }
                    case "link": {
                        findCSS(page);
                    }
                    case "script": {

                    }
                }
            }
            page.makeVisited();
            download(page, new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8.name())));
        } catch (IOException e) {
            System.err.println("Error crawling " + link + ": " + e.getMessage());
        }
    }

    private boolean findBeginOfTag() throws IOException {
        while (ch != -1) {
            if (ch == '<') {
                nextChar();
                if (ch != '/') {
                    break;
                }
            }
            nextChar();
        }
        return ch != -1;
    }

    private String readOpenTag() throws IOException {
        StringBuilder word = new StringBuilder();
        while (!Character.isWhitespace(ch) && ch != '>') {
            word.append((char) ch);
            nextChar();
        }
        return word.toString();
    }

    private int nextChar() throws IOException {
        content.append((char) ch);
        ch = input.read();
        return ch;
    }

    private void safeNextChar() throws IOException {
        ch = input.read();
    }

    private void skipSpaces() throws IOException {
        while (Character.isWhitespace(ch)) {
            safeNextChar();
        }
    }

    private void skipComment() throws IOException {
        int ch1 = ch;
        int ch2 = input.read();
        int ch3 = input.read();
        while (!(ch1 == '-' && ch2 == '-' && ch3 == '>')) {
            ch1 = ch2;
            ch2 = ch3;
            ch3 = input.read();
        }
    }

    private boolean isBracket(int c) {
        return c == '"' || c == '\'';
    }

    private String findBracketsInput() throws IOException{
        while (!isBracket(ch)) { nextChar(); }
        nextChar();
        skipSpaces();
        StringBuilder sb = new StringBuilder();
        while (!isBracket(ch) && !Character.isWhitespace(ch)) {
            sb.append((char) ch);
            safeNextChar();
        }
        return sb.toString()
                .replaceAll("&amp;", "&")
                .replaceAll("&mdash;", "\u2014");
    }

    private void findImage(Page page) throws IOException{
        int ch1 = ch;
        int ch2 = nextChar();
        int ch3 = nextChar();
        while (!(ch1 == 's' && ch2 == 'r' && ch3 == 'c')) {
            ch1 = ch2;
            ch2 = ch3;
            ch3 = nextChar();
        }
        String link = findBracketsInput();
        URL imageUrl = new URL(page.getUrl(), link);
        link = imageUrl.toString();

        Image image;
        if (!images.containsKey(link)) {
            image = new Image(directory, imageUrl);
            images.put(link, image);
            download(image, image.getUrl().openConnection().getInputStream());
        } else {
            image = images.get(link);
        }
        content.append(image.getFile());
    }

    private String findHrefInput() throws IOException{
        int ch1 = ch;
        int ch2 = nextChar();
        int ch3 = nextChar();
        int ch4 = nextChar();
        if (ch1 == '>' || ch2 == '>' || ch3 == '>') { return ""; }
        while(!(ch1 == 'h' && ch2 == 'r' && ch3 == 'e' && ch4 == 'f') && ch4 != '>') {
            ch1 = ch2;
            ch2 = ch3;
            ch3 = ch4;
            ch4 = nextChar();
        }
        if (ch4 == '>') { return ""; }
        return findBracketsInput();
    }

    private void findCSS(Page page) throws IOException {
        String link = findHrefInput();
        if (link.length() == 0) {
            return;
        }

        URL url = new URL(page.getUrl(), link);
        link = url.toString();
        Style style;
        if (styles.containsKey(link)) {
            style = styles.get(link);
        } else {
            style = new Style(directory, url);
            styles.put(link, style);
            download(style, style.getUrl().openConnection().getInputStream());
        }
        content.append(style.getFile());
    }

    private void findLink(Page page) throws IOException {
        String link = findHrefInput();
        if (link.length() == 0) {
            return;
        }

        URL url;
        try {
            url = new URL(page.getUrl(), link);
        } catch (IOException e) {
            content.append(link).append('"');
            return;
        }

        link = url.toString();
        int index = link.length() - 1;
        while (index > 0 && link.charAt(index) != '/' && link.charAt(index) != '#') {
            index--;
        }
        if (link.charAt(index) == '#') {
            link = link.substring(0, index);
        }


        Page currPage;
        if (!pages.containsKey(link)) {
            currPage = new Page(directory, url);
            pages.put(link, currPage);
        } else {
            currPage = pages.get(link);
        }
        content.append(currPage.getFile());
        if (!pages.get(link).isVisited()) {
            nextQueue.add(link);
        }
    }

    private void createDirectory(final Path directory) throws IOException {
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        if (!Files.isDirectory(directory)) {
            throw new IOException(directory + " is not a directory");
        }
    }

    private void download(final AbstractHtmlObject file, final InputStream in) {
        if (Files.exists(Paths.get(file.getFile()))) {
            return;
        }
        System.out.println(file.getFile());
        try {
            createDirectory(Paths.get(file.getDirectory()));
            try (FileOutputStream fos = new FileOutputStream(new File(file.getDirectory() + file.getName()))) {
                int ch;
                while ((ch = in.read()) != -1)
                {
                    fos.write(ch);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot create or rewrite " + file.getFile() + ": " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error while downloading from " + file.getUrl().toString() + ": " + e.getMessage());
        }
    }

}
