package crawler;

import java.io.IOException;

/**
 * @author Vadzimko
 */
public interface WebCrawler {
    Page crawl(String url, int depth) ;
}
