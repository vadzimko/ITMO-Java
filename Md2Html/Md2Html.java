package md2html;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Md2Html {
    private static final String encoding = "UTF-8";

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("usage: java Md2Html <file.in> <file.out>");
            return;
        }
        convert(args[0], args[1]);
    }

    private static void convert(final String fileIn, final String fileOut) {
        StringBuilder input = new StringBuilder();
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileIn), encoding))) {
                int ch = reader.read();
                while (ch != -1) {
                    input.append((char) ch);
                    ch = reader.read();
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported encoding: " + encoding);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find input file: " + fileIn);
        } catch (IOException e) {
            System.err.println("Error while reading from: " + fileIn);
        }

        try {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOut), encoding))) {
                StringBuilder string = new StringBuilder();

                int inputPos = 0;
                input.append("  ");
                while (inputPos < input.length()) {
                    int last = input.charAt(inputPos++);
                    int curr = input.charAt(inputPos++);

                    while (inputPos < input.length() && (last == '\r' || last == '\n')) {
                        last = curr;
                        curr = input.charAt(inputPos++);
                    }

                    while (inputPos < input.length() && (last != '\n' || curr != '\r') && (last != '\n' || curr != '\n')) {
                        string.append((char) last);
                        last = curr;
                        curr = input.charAt(inputPos++);
                    }

                    while (string.charAt(string.length() - 1) == '\r' || string.charAt(string.length() - 1) == '\n') {
                        string.delete(string.length() - 1, string.length());
                    }

                    List<Integer> isPairTag = checkPairs(string);

                    int pos = 0;
                    int ch = string.charAt(pos++);
                    int size = string.length();

                    int headerCounter = 0;
                    while (pos < size && ch == '#') {
                        headerCounter++;
                        ch = string.charAt(pos++);
                    }

                    if ((char) ch == ' ' && headerCounter > 0) {
                        writer.write("<h" + headerCounter + ">");
                        convertInput(string, pos, writer, isPairTag);
                        writer.write("</h" + headerCounter + ">");
                    } else {
                        writer.write("<p>");
                        for (int i = 0; i < headerCounter; i++) {
                            writer.write("#");
                        }
                        convertInput(string, pos - 1, writer, isPairTag);
                        writer.write("</p>");
                    }
                    writer.newLine();
                    string.delete(0, string.length());
                }
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported encoding: " + encoding);
        } catch (FileNotFoundException e) {
            System.err.println("Cannot create or change: " + fileOut);
        } catch (IOException e) {
            System.err.println("Failed to write to the output file: " + fileOut);
        }
    }

    private static List<Integer> checkPairs(StringBuilder string) {
        List<Integer> pairs = new ArrayList<>();
        List<Integer> tags = new ArrayList<>();
        int last;
        int curr;
        string.append("  ");

        for (int pos = 0; pos < string.length() - 1; pos++) {
            last = string.charAt(pos);
            curr = string.charAt(pos + 1);
            if (last == '\\') {
                pos++;
                continue;
            }
            int key = keyOf(last, curr);
            if (key != 0) {
                tags.add(key);
                pairs.add(0);
                if (key < 0) {
                    pos++;
                }
            }
        }

        for (int l = 0; l < tags.size() - 1; l++) {
            while (l < tags.size() - 1 && tags.get(l) == 0) {
                l++;
            }
            if (l == tags.size() - 1) {
                break;
            }

            int r = l + 1;
            while (r < tags.size() && !tags.get(r).equals(tags.get(l))) {
                r++;
            }
            if (r == tags.size()) {
                pairs.set(l, 0);
            } else {
                pairs.set(l, 1);
                pairs.set(r, -1);
                tags.set(r, 0);
            }
            tags.set(l, 0);
        }
        return pairs;
    }

    private static void convertInput(final StringBuilder string, int pos, final BufferedWriter writer, final List<Integer> isPairTag) throws IOException{
        int last;
        int curr;
        last = string.charAt(pos++);
        curr = string.charAt(pos++);

        int tagNum = 0;
        while (pos < string.length()) {
            if (last == '\\') {
                writer.write(curr);
                last = string.charAt(pos++);
                if (pos < string.length()) {
                    curr = string.charAt(pos++);
                }
                continue;
            }

            int key = keyOf(last, curr);
            if (key != 0) {
                switch (isPairTag.get(tagNum)) {
                    case 1: {
                        writer.write(valueOfKeyBegin(key));
                        break;
                    }
                    case 0: {
                        writer.write(last);
                        break;
                    }
                    case -1: {
                        writer.write(valueOfKeyEnd(key));
                        break;
                    }
                }

                if (key < 0) {
                    last = string.charAt(pos++);
                } else {
                    last = curr;
                }
                curr = string.charAt(pos++);
                tagNum++;
                continue;
            }

            if (isTagSymbol(last)) {
                writer.write(textOf(last));
            } else {
                writer.write((char) last);
            }

            last = curr;
            curr = string.charAt(pos++);
        }
    }

    private static int keyOf(final int x, final int y) {
        switch ((char) x + "" + (char) y) {
            case "**":
                return -1;
            case "__":
                return -2;
            case "--":
                return -3;
            case "++":
                return -4;
        }

        switch (x) {
            case '`':
                return 1;
            case '*':
                return 2;
            case '_':
                return 3;
            case '~':
                return 4;
            default:
                return 0;
        }
    }

    private static String valueOfKeyBegin(final int key) {
        switch (key) {
            case -1:
            case -2:
                return "<strong>";
            case -3:
                return "<s>";
            case -4:
                return "<u>";
            case 1:
                return "<code>";
            case 2:
            case 3:
                return "<em>";
            case 4:
                return "<mark>";
            default:
                return "";
        }
    }

    private static String valueOfKeyEnd(final int key) {
        switch (key) {
            case -1:
            case -2:
                return "</strong>";
            case -3:
                return "</s>";
            case -4:
                return "</u>";
            case 1:
                return "</code>";
            case 2:
            case 3:
                return "</em>";
            case 4:
                return "</mark>";
            default:
                return "";
        }
    }

    private static boolean isTagSymbol(final int x) {
        return x == '<' || x == '>' || x == '&';
    }

    private static String textOf(int x) {
        switch (x) {
            case '<':
                return "&lt;";
            case '>':
                return "&gt;";
            case '&':
                return "&amp;";
            default:
                return "";
        }
    }
}
