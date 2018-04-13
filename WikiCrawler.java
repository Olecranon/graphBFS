// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may only include libraries of the form java.*)

/**
* @author 
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



// http://www.codebytes.in/2015/11/simple-web-crawler-using-bfs-java.html
public class WikiCrawler
{
	static final String BASE_URL = "https://en.wikipedia.org";
	
	private String seedUrl;
	private int max;
	private ArrayList<String> topics;
	private String fileName;
	
	private ArrayList<String> edgesInfo;

	//Queue for BFS
	private Queue<String> q;
	
	//URLs already visited
	private ArrayList<String> marked;
	
	private ArrayList<String> found;
	
	//URL Pattern regex
	private String regexp = "\"/wiki/[^\"#:]+\"";

	// nuber of request for crawling
	private int request;
	
	// write graph to a local file

	
	public WikiCrawler(String seedUrl, int max, ArrayList<String> topics, String fileName)
	{
		this.seedUrl = seedUrl;
		this.max = max;
		this.topics = topics;
		this.fileName = fileName;
		
		q = new LinkedList<>();
		marked = new ArrayList<>();
		edgesInfo = new ArrayList<>();
		
		request = 0;
		
	}

	public void crawl() throws Exception {
		String root = BASE_URL + seedUrl;
		q.add(root);
		
		while(!q.isEmpty()) {
			
			// find the first element of the queue
			String firstURLString = q.poll();
            
            boolean ok = false;
            String webString = "";
            while(!ok) {
            	try {
            		// check the content of the link
            		ok = true;
            		webString = getWebAsString(firstURLString);
            		
            		request++;
            		//Wait for at least 3 seconds after every 25 requests.
            		if(request%25 == 0) {
            			Thread.sleep(3000);
            		}
            		
            	} catch(Exception e){
            		System.out.println(firstURLString + " is DeadLink, So, get the next url Link");
            		firstURLString = q.poll();
            		ok = false;            		
            	}
            }
            
            String nodeA = firstURLString;
            String nodeB = "";
            
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(webString);
            
            while(matcher.find()) {
            	String tempURL = BASE_URL + matcher.group().replace("\"", "");
            	            	
        		// get the web content of the next page
        		String webString_next = getWebAsString(tempURL);
        		request++;
        		
        		//Wait for at least 3 seconds after every 25 requests.
        		if(request%25 == 0) {
        			Thread.sleep(3000);
        		}
        		
        		boolean topicAllExist = true;
        		if(topics.size() < 1) {
        			topicAllExist = true;
        		} else {            			
        			// check web content has ALL the topic listed
        			for (String eachTopic:topics) {
        				if(!webString_next.contains(eachTopic)) {
        					System.out.println(tempURL + "Does not has topic of: " + eachTopic);
        					topicAllExist = false;
        					break;
        				}
        			}           			
        		}
        		
    			if (!topicAllExist) {
    				continue;
    			}
            	
    			
            	if( (!marked.contains(tempURL)) &&  topicAllExist ) {
            		
            		// Add urls that only contains topics
            		marked.add(tempURL);
            		q.add(tempURL);
            		
            		nodeB = tempURL;
            		String edgeInfo_i = nodeA.replace("\"", "").replace(BASE_URL, "") + " " + 
            						  nodeB.replace("\"", "").replace(BASE_URL, "");
            		edgesInfo.add(edgeInfo_i);  
            		
            		if(marked.size() >= max) {
            			writeGraphToLocalFile();
            			return;
            		}
            	}
            }    
		}
		
		//after crawing. write to local file
		
	}
	
	// need to delete later
	public void printMarked() {
		int i = 1;
		for(String s: edgesInfo) {
			System.out.println(i + ", " + s);
			i++;
		}
	}
	
	private void writeGraphToLocalFile() throws Exception {
		FileWriter fw = new FileWriter(fileName);
		fw.write(max + "\n");
		for(String s: edgesInfo) {
			fw.write(s + "\n");
			fw.flush();	
		}
		fw.close();
	}
	
	/**
	 * private method to get the string representation of a webpage
	 * @param urlString
	 * @return
	 * @throws Exception
	 */
	private String getWebAsString(String urlString) throws Exception {
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
        StringBuilder sb = new StringBuilder();
        String s;
        
        
        boolean actualTestContent = false;
        while((s = br.readLine())!=null){        	
        	if(s.contains("<p>")) {
        		actualTestContent = true;
        	}
        	// only read the web after the actualTestContent
        	if(actualTestContent) {
        		sb.append(s);
        	}
        }
        s = sb.toString();
        return s;
	}

}