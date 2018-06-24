package crawler;

import java.io.IOException;
import java.net.URL;

/**
 * @author Vadzimko
 */
public abstract class AbstractHtmlObject {
    private final String directory;
    private final String name;
    private final URL url;

    public AbstractHtmlObject(final String directory, final URL url) throws IOException {
        this.url = url;
        if (url.getPath().length() > 1) {
            String str = url.getPath();
            int index = str.length() - 1;
            while (index >= 0 && str.charAt(index) != '/') {
                index--;
            }
            if (index < 0 && str.charAt(0) != '/') {
                this.directory = directory + url.getHost();
                name = str;
            } else {
                this.directory = directory + url.getHost() + str.substring(0, index).replaceAll("/", "\\\\") + '\\';
                name = str.substring(index + 1);
            }
        } else {
            name = "index.html";
            this.directory = directory;
        }
    }

    public URL getUrl() {
        return url;
    }

    public String getDirectory() {
        return directory;
    }

    public String getName() {
        return name;
    }

    public String getFile() {
        return directory + name;
    }
}
