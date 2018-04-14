// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add member fields and additional methods)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may only include libraries of the form java.*)

/**
* @author 
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class NetworkInfluence {
	// NOTE: graphData is an absolute file path that contains graph data, NOT the raw graph data itself
	
	private String graphData;
	
	// number of vertices of a given graph
	private int n;
	
	//TODO make it private later
	public HashMap<String, ArrayList<String>> adjListMap;
	
	// a list to store all the nodes in the graph.
	private Set<String> nodeList;

	public NetworkInfluence(String graphData) throws Exception {
		this.graphData = graphData;
		
		// need to scan the file
		Scanner s = new Scanner(new File(graphData));
		
		// number of vertices
		n = Integer.parseInt(s.nextLine());
		
		// construct a graph Adjacency List Map, key is node, val is adjacency List
		adjListMap = new HashMap<>();
		
		nodeList = new HashSet<String>();
		
		while(s.hasNextLine()) {
			String edgeInfo = s.nextLine();
			String NodeA = edgeInfo.split(" ")[0];
			String NodeB = edgeInfo.split(" ")[1];
			
			// add the node to the node list, set is already take care of duplication
			nodeList.add(NodeA);
			nodeList.add(NodeB);
			
			// if NodeA first shown up, add it to the graphMap & let NodeB as its neighbor
			if(!adjListMap.containsKey(NodeA)) {
				ArrayList<String> neighbors = new ArrayList<>();
				neighbors.add(NodeB);
				adjListMap.put(NodeA, neighbors);
			} else {
				ArrayList<String> neighbors_existing =  adjListMap.get(NodeA);
				
				//Add B to the neighbor List
				neighbors_existing.add(NodeB);
				// update the neighbor List
				adjListMap.put(NodeA, neighbors_existing);
			}			
		}			
	}

	
	public int outDegree(String v) {
		ArrayList<String> v_neigbors = adjListMap.get(v);
		
		if(v_neigbors != null) {
			return v_neigbors.size();
		} else {
			return 0;
		}		
	}

	
	//ref: https://codereview.stackexchange.com/questions/149120/bfs-shortest-path-unweighted-directed-graph
	public ArrayList<String> shortestPath(String u, String v) {

		//Queue for BFS
		Queue<String> q = new LinkedList<>();
		q.add(u);
		
		ArrayList<String> visited = new ArrayList<>();
		visited.add(u);
		
		//Create a map to keep track of nodes and its previous nodes
		HashMap<String, String> prev = new HashMap<>();
		prev.put(u, null);		
		
		while(!q.isEmpty()) {
			
			String element = q.poll();
			
			if (element.equals(v)){
				// it's the path without source, in reversed order
				ArrayList<String> pathFromB2A = traceBackPath(element, prev);
				// add the source node
				pathFromB2A.add(u);
				Collections.reverse(pathFromB2A);
				return pathFromB2A;
			}
			
			ArrayList<String> neighbours = adjListMap.get(element);
			if(neighbours != null) {
				
				for (int i = 0; i < neighbours.size(); i++) {
					String n = neighbours.get(i);
					if (n != null && !visited.contains(n)) {
						q.add(n);
						visited.add(n);
						
						// element is n's previous nodes
						prev.put(n, element);
					}					
				}
			}						
		}		
		return new ArrayList<>();
	}

	/**
	 * Private method to trace from target back to source
	 * Return list has no source node, so need to add and reverse it 
	 * @param target
	 * @param prev
	 * @return
	 */
	private static ArrayList<String> traceBackPath(String target, HashMap<String, String> prev){
		ArrayList<String> path = new ArrayList<>();
		String u = target;
		while(prev.get(u) != null){
			path.add(u);
			u = prev.get(u);
		}		
		return path;
	}
	
	
	public int distance(String u, String v) {
		ArrayList<String> shortestPath_u_v = shortestPath(u, v);
		
		int dist = shortestPath_u_v.size();
		// the shortestPath_u_v has all the nodes, therefore, the distance is the edges, which is 1 shorter
		return dist-1;
	}

	public int distance(ArrayList<String> s, String v) {
		// implementation
		int minDist = 100;
		for(String x:s){
			int dist_xy = distance(x, v);
			if(dist_xy >= 0){
				if (dist_xy<=minDist){
					minDist = dist_xy;
				}
			}
		}
		return minDist;
	}

	
	public float influence(String u) {
		// calculate the distance from u to all the other nodes in the graph
		// nodeList is calculated in the constructor.
		
		HashMap<Integer, Set<String>> infMap = new HashMap<>();		
		for(String y:nodeList){
				
			int dist_u_y = distance(u, y);
			if (dist_u_y < 0){
				continue;
			}
			
			if(!infMap.keySet().contains(dist_u_y)){
				Set<String> k_hop = new HashSet<String>();
				k_hop.add(y);
				infMap.put(dist_u_y, k_hop);
			} else {
				Set<String> k_hop_existing = infMap.get(dist_u_y);
				k_hop_existing.add(y);
				infMap.put(dist_u_y, k_hop_existing);
			}			
		}
		
		// calculate the infuence of u
		float ans = 0;
		for (Integer i: infMap.keySet()){
			int setSize = infMap.get(i).size();
			float temp = (float) (1.0/Math.pow(2, i) * setSize);
			
			System.out.println("dist: " + i + ", Size y is: " + setSize);			
			ans = ans + temp;
		}

		return ans;
	}

	public float influence(ArrayList<String> s) {
		// implementation

		// replace this:
		return -1f;
	}

	public ArrayList<String> mostInfluentialDegree(int k) {
		// implementation

		// replace this:
		return null;
	}

	public ArrayList<String> mostInfluentialModular(int k) {
		// implementation

		// replace this:
		return null;
	}

	public ArrayList<String> mostInfluentialSubModular(int k) {
		// implementation

		// replace this:
		return null;
	}
}