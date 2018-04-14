import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
		
		String graphData = "testing.txt";
//		WikiCrawler w = new WikiCrawler("/wiki/Complexity_theory", 20, topics, graphData);
//		w.crawl();
//		w.printMarked();

		
		NetworkInfluence ni = new NetworkInfluence(graphData);
		System.out.println("Graph----------");
		HashMap<String, ArrayList<String>> mapInfo = ni.adjListMap;
		for (String k:mapInfo.keySet()) {
			
			System.out.print("Key is " + k + ": Values: " );
			for (String n:mapInfo.get(k)) {
				System.out.print(n + ", ");
			}
			System.out.println();
		}		
		
		String u = "40";
		String v = "40";
		System.out.println("outdegree of " + u + ": " + ni.outDegree(u));
		ArrayList<String> path = ni.shortestPath(u, v);
		for(String p : path) {
			System.out.print(p + " ->");
		}
		System.out.println();
		System.out.println(ni.distance(u, v));
		
		
		///// Test the influence
		NetworkInfluence ni2 = new NetworkInfluence("infTest");
		ArrayList<String> path2 = ni2.shortestPath("G", "K");
		System.out.println("distance : " + ni2.distance("G", "K"));
		for(String p : path2) {
			System.out.print(p + " ->");
		}
		System.out.println();
		
		System.out.println(ni2.influence("G"));

	}
	

}
