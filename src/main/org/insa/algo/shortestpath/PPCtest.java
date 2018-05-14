package org.insa.algo.shortestpath;


import org.insa.graph.*;
import org.insa.algo.*;
import org.insa.algo.shortestpath.*; 
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;



public class PPCtest{

	 // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc x1x2, x1x3, x2x4, x2x5, x2x6, x3x1, x3x2, x3x6, x5x3, x5x4, x5x6, x6x5;

    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        x1x2 = Node.linkNodes(nodes[0], nodes[1], 7, speed10, null);
        x1x3 = Node.linkNodes(nodes[0], nodes[2], 8, speed10, null);
        x2x4 = Node.linkNodes(nodes[1], nodes[3], 4, speed10, null);
        x2x5 = Node.linkNodes(nodes[1], nodes[4], 1, speed10, null);
        x2x6 = Node.linkNodes(nodes[1], nodes[5], 5, speed10, null);
        x3x1 = Node.linkNodes(nodes[2], nodes[0], 7, speed10, null);
        x3x2 = Node.linkNodes(nodes[2], nodes[1], 2, speed10, null);
        x3x6 = Node.linkNodes(nodes[2], nodes[5], 2, speed10, null);
        x5x3 = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
        x5x4 = Node.linkNodes(nodes[4], nodes[3], 2, speed10, null);
        x5x6 = Node.linkNodes(nodes[4], nodes[5], 3, speed10, null);
        x6x5 = Node.linkNodes(nodes[5], nodes[4], 3, speed10, null);
        
        
        
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
    }
    
    
    
  //Test Dijkstra
    @Test
    public  void Testdisj() {
    	System.out.println("Dijkstra Algorithm: ");  
        for(int origin=0;origin<nodes.length;origin++) {
        	for(int dest=0;dest<nodes.length;dest++) {
        		if(origin!=dest) {
        			ShortestPathData data=new ShortestPathData(graph,nodes[origin],nodes[dest],ArcInspectorFactory.getAllFilters().get(0));
        			
        			DijkstraAlgorithm DJ= new DijkstraAlgorithm(data);
        			ShortestPathSolution solutionDJ=DJ.doRun();
        			
        			BellmanFordAlgorithm BF= new BellmanFordAlgorithm(data);
        			ShortestPathSolution solutionBF=BF.doRun();
        			
        			Path cheminDJ=solutionDJ.getPath();
        			Path cheminBF=solutionBF.getPath();
        			
        			
	        		if(cheminDJ!=null && cheminDJ.isValid()) {
	        			if(cheminDJ.getLength()==cheminBF.getLength()) {
		        			float taille=cheminDJ.getLength();
		        			Arc dernierArc=cheminDJ.getArcs().get(cheminDJ.getArcs().size()-1);
		        			int pred=dernierArc.getOrigin().getId()+1;
		        			System.out.print("| "+taille+",x"+pred+" |");
	        			}
	        			else {
	        				break;
	        			}
	        		}
	        		else {
	            		System.out.print("|   -   |");
	            	}

        		}
        		else {
        			System.out.print("|   -   |");
        		}
        	}
        	//saut à la ligne
        	System.out.println();
        }
    }
}
