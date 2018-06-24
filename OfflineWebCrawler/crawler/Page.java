package crawler;

import java.io.IOException;
import java.net.URL;

/**
 * @author Vadzimko
 */
public class Page extends AbstractHtmlObject {
    private boolean visited;
    private StringBuilder content = new StringBuilder();

    public Page(final String directory, final URL url) throws IOException {
        super(directory, url);
        visited = false;
    }

    public void makeVisited() {
        visited = true;
    }

    public StringBuilder getContent() {
        return content;
    }

    public boolean isVisited() {
        return visited;
    }
}