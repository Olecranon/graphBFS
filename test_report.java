import java.util.ArrayList;
import java.util.HashMap;

public class test_report {

	public static void main(String[] args) throws Exception {
		ArrayList<String> topics = new ArrayList<>();
		
		String graphData = "WikiCS.txt";
		WikiCrawler w = new WikiCrawler("/wiki/Computer_Science", 100, topics, graphData);
		w.crawl();
		
		NetworkInfluence ni = new NetworkInfluence(graphData);
		
		///Top 10 most infuential nodes computed by mostInfluentialDegree
		System.out.println("Top 10 most infuential nodes computed by mostInfluentialDegree");
		ArrayList<String> top10 = ni.mostInfluentialDegree(10);		
		for(String n:top10){
			float influence = ni.influence(n);
			System.out.println(n + ": " + influence);
		}
		
		System.out.println("-------------");
		///Top 10 most infuential nodes computed by mostInfluentialModular
		System.out.println("Top 10 most infuential nodes computed by mostInfluentialModular");
		ArrayList<String> top10_modular = ni.mostInfluentialModular(10);		
		for(String n:top10){
			float influence = ni.influence(n);
			System.out.println(n + ": " + influence);
		}
		
		
	}

}
