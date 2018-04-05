package org.insa.algo.shortestpath;

import java.util.Arrays;

import org.insa.graph.*;
import org.insa.algo.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        
        //public BinaryHeap(BinaryHeap<E> heap)
        BinaryHeap<Label> file_traitement=new BinaryHeap<Label>();
        
        Label current;
        for (Node node : graph) {       	
        	if(node==data.getOrigin()) {
        		current=new Label(0,node);
        	}
        	else {
        		current=new Label(Double.POSITIVE_INFINITY,node);
        	}
        	file_traitement.insert(current);
        }
     	//Arrays.fill(distances, Double.POSITIVE_INFINITY);
     	//distances[data.getOrigin().getId()] = 0;
        
        return solution;
    }

}
