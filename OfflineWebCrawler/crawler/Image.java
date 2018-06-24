package crawler;

import java.io.IOException;
import java.net.URL;

/**
 * @author Vadzimko
 */
public class Image extends AbstractHtmlObject {
    public Image(final String directory, final URL url) throws IOException {
        super(directory, url);
    }
}