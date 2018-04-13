import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class test {
	static final String BASE_URL = "https://en.wikipedia.org";
	
	public static void main(String[] args) throws Exception {
		ArrayList<String> topics = new ArrayList<>();
//		topics.add("Iowa State");
//		topics.add("Cyclones");
		
		WikiCrawler w = new WikiCrawler("/wiki/Complexity_theory", 20, topics, "WikiCC.txt");
		w.crawl();
		w.printMarked();

	}
	

}
