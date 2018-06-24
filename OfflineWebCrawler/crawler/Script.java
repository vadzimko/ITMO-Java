package crawler;

import java.io.IOException;
import java.net.URL;

/**
 * @author Vadzimko
 */
public class Script extends AbstractHtmlObject {
    public Script(final String directory, final URL url) throws IOException {
        super(directory, url);
    }
}