import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Sum {
    public static void main(String[] args) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            if (args.length == 0) {
                try {
                    findHash(md, System.in, "-");
                } catch (IOException e) {
                    System.err.println("Error while reading from stdin: " + e.getMessage());
                }
            } else {
                for (String name : args) {
                    try {
                        findHash(md, new FileInputStream(name), name);
                    } catch (SecurityException e) {
                        System.err.println("Cannot get access to " + name + ": " + e.getMessage());
                    } catch (IOException e) {
                        System.err.println("Error while reading from: " + name + ": " + e.getMessage());
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            System.err.println("JVM doesn't support SHA-256 algorithm");
        }
    }

    private static void findHash(MessageDigest md, InputStream is, final String name) throws IOException {
        byte[] buf = new byte[4096];
        int size;
        while ((size = is.read(buf)) != -1) {
            md.update(buf, 0, size);
        }
        System.out.println(DatatypeConverter.printHexBinary(md.digest()) + " *" + name);
    }

}