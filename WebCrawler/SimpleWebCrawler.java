package crawler;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Vadzimko
 */

public class SimpleWebCrawler implements WebCrawler {
    private Map<String, Page> visited;
    private Map<String, Image> images;
    private Map<String, URL> pageURLs;
    private Downloader downloader;
    private BufferedReader input;
    private int ch;
    private URL pageUrl;
    private Queue<String> nextQueue;
    private List<List<String>> links;

    SimpleWebCrawler(Downloader downloader) throws IOException {
        this.downloader = downloader;
    }

    public Page crawl(String url, int depth) {
        visited = new HashMap<>();
        images = new HashMap<>();
        pageURLs = new HashMap<>();
        links = new ArrayList<>();
        nextQueue = new ArrayDeque<>();
        Queue<String> currQueue = new ArrayDeque<>();
        currQueue.add(url);
        while (depth > 0) {
            for (String s : currQueue) {
                if (!visited.containsKey(s)) {
                    crawlPage(s);
                }
            }
            currQueue = nextQueue;
            nextQueue = new ArrayDeque<>();
            depth--;
        }
        for (String link : currQueue) {
            visited.put(link, new Page(link, ""));
        }
        for (List<String> l : links) {
            Page page = visited.get(l.get(0));
            for (int i = 1; i < l.size(); i++) {
                page.addLink(visited.get(l.get(i)));
            }
        }
        return visited.get(url);
    }

    private void crawlPage(String link) {
        try {
            input = new BufferedReader(new InputStreamReader(downloader.download(link), "utf-8"));
        } catch (IOException e) {
            visited.put(link, new Page(link, ""));
            System.err.println(link);
            return;
        }

        List<String> linksFromPage = new ArrayList<>();
        linksFromPage.add(link);
        try {
            do {
                findBeginOfTag();
            } while (!readOpenTag().equals("title"));

            Page page = new Page(link, findTitle());
            visited.put(link, page);

            if (!pageURLs.containsKey(link)) {
                pageURLs.put(link, new URL(link));
            }
            pageUrl = pageURLs.get(link);

            while (true) {
                if (!findBeginOfTag()) {
                    break;
                }
                String tag = readOpenTag();

                switch (tag) {
                    case "!--": {
                        skipComment();
                        break;
                    }
                    case "a": {
                        findLink(page, linksFromPage);
                        break;
                    }
                    case "img": {
                        findImage(page);
                        break;
                    }
                }

            }
            links.add(linksFromPage);
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
        return word.toString().toLowerCase();
    }

    private void nextChar() throws IOException {
        ch = input.read();
    }

    private void skipSpaces() throws IOException {
        while (Character.isWhitespace(ch)) {
            nextChar();
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

    private String findTitle() throws IOException {
        while (ch != '>') { nextChar(); }
        nextChar();

        StringBuilder sb = new StringBuilder();
        while (sb.length() <= 7 || !sb.substring(sb.length() - 7).equals("</title")) {
            sb.append((char) ch);
            nextChar();
        }

        sb.delete(sb.length() - 7, sb.length());

        return sb.toString()
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&nbsp;", "\u00A0")
                .replaceAll("&mdash;", "\u2014")
                .replaceAll("&reg;", "\u2014")
                .replaceAll("&amp;", "\u00AE");
    }


    private String findBracketsInput() throws IOException{
        while (!isBracket(ch)) { nextChar(); }
        nextChar();
        skipSpaces();
        StringBuilder sb = new StringBuilder();
        while (!isBracket(ch) && !Character.isWhitespace(ch)) {
            sb.append((char) ch);
            nextChar();
        }
        return sb.toString()
                .replaceAll("&mdash;", "\u2014")
                .replaceAll("&amp;", "&");
    }

    private boolean isBracket(int c) {
        return c == '"' || c == '\'';
    }

    private void findImage(Page page) throws IOException{
        int ch1 = ch;
        int ch2 = input.read();
        int ch3 = input.read();
        while (!(ch1 == 's' && ch2 == 'r' && ch3 == 'c')) {
            ch1 = ch2;
            ch2 = ch3;
            ch3 = input.read();
        }

        String link = findBracketsInput();
        URL url;
        try {
            url = new URL(pageUrl, link);
        } catch (IOException e) {
            System.err.println(link);
            images.put(link, new Image(link, ""));
            return;
        }
        link = url.toString();

        if (images.containsKey(link)) {
            page.addImage(images.get(link));
        } else {
            Image image = new Image(link, String.valueOf(link.hashCode()) + extension(link));
            page.addImage(image);
            images.put(link, image);
            try (InputStream in = downloader.download(link);
                 FileOutputStream fos = new FileOutputStream(new File(image.getFile()))){
                int ch;
                while ((ch = in.read()) != -1)
                {
                    fos.write(ch);
                }
            } catch (FileNotFoundException e) {
                System.err.println("Cannot create or rewrite " + image.getFile() + ": " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Error while downloading " + link + ": " + e.getMessage());
            }
        }
    }

    private String extension(String link) {
        int i = link.length() - 1;
        while (i >= 0 && link.charAt(i) != '.' && link.charAt( i) != '/') {
            i--;
        }
        if (link.charAt(i) == '.') {
            return link.substring(i);
        } else {
            return "";
        }
    }

    private void findLink(Page page, List<String> list) throws IOException {
        int ch1 = ch;
        int ch2 = input.read();
        int ch3 = input.read();
        int ch4 = input.read();
        if (ch1 == '>' || ch2 == '>' || ch3 == '>') { return; }
        while(!(ch1 == 'h' && ch2 == 'r' && ch3 == 'e' && ch4 == 'f'
            || ch1 == 'H' && ch2 == 'R' && ch3 == 'E' && ch4 == 'F') && ch4 != '>') {
            ch1 = ch2;
            ch2 = ch3;
            ch3 = ch4;
            ch4 = input.read();
        }
        if (ch4 == '>') { return; }

        String link = findBracketsInput();
        URL url;
        try {
            url = new URL(pageUrl, link);
        } catch (IOException e) {
            System.err.println(link);
            visited.put(link, new Page(link, ""));
            list.add(link);
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

        pageURLs.put(page.getUrl(), url);
        if (!visited.containsKey(link)) {
            nextQueue.add(link);
        }

        list.add(link);
    }
}