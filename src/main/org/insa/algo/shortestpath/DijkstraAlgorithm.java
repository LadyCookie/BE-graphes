package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.insa.graph.*;
import org.insa.algo.AbstractSolution.Status;
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
        
        ArrayList<Label> couts=new ArrayList<Label>(graph.size());
        
        //public BinaryHeap(BinaryHeap<E> heap)
        BinaryHeap<Label> file_traitement=new BinaryHeap<Label>();
        
        //initialisation des coûts
        Label currentL;
        Node minimum=data.getOrigin();
        
        for (Node node : graph) { //Je considère qu'il parcours dans l'ordre des index des noeuds: potentielle source d'erreur       	
        	if(node==minimum) {
        		currentL=new Label(0,node);
        		couts.add(currentL);
        		file_traitement.insert(currentL);
        	}
        	else {
        		currentL=new Label(Double.POSITIVE_INFINITY,node);
        		couts.add(currentL);
        	}
        	
        }
     	
        currentL=file_traitement.deleteMin();
        //à la première itération, minimum est égal à l'origine
        while(minimum!=data.getDestination() && !file_traitement.isEmpty()) { //soit on atraiter        	
        	Node currentNode=currentL.getNode(); //On récupère le noeud(père) sur lequel on va travailler
        	
        	Iterator<Arc> successeur=currentNode.iterator(); //On récupère les arcs vers les fils de currentNode     	
        	Arc arcVersFils=successeur.next(); 
        	
        	while(successeur.hasNext()) { //On parcourt les successeurs de currentNode
        		
        		//on calcule le PPC à partir du noeud actuel
        		double coutActuel=currentL.getLabel()+arcVersFils.getLength();
        		Label newLabel=new Label(coutActuel,currentNode);
        		
        		//On compare au couts jusqu'alors enregistré, Si newLabel plus court, on actualise le cout
        		if(couts.get(arcVersFils.getDestination().getId()).compareTo(newLabel) >0 ) {
        			if(couts.get(arcVersFils.getDestination().getId()).getLabel() != Double.POSITIVE_INFINITY){
        				file_traitement.remove(couts.get(arcVersFils.getDestination().getId())); //Pour éviter d'avoir un même noeud 2fois
        			}
        			
        			couts.set(arcVersFils.getDestination().getId(),newLabel);
        			file_traitement.insert(newLabel);
        		}
        		arcVersFils=successeur.next();
        		
        	}
        	
        	currentL=file_traitement.deleteMin(); //On cherche le noeud à traiter en priorité et on l'enlève du tas, ce qui équivaut à marquer le sommet
        	minimum=currentL.getNode(); //Permet de voir si on traite la destination (ie fin de l'algorithme)
        }
        
        //Dans le cas où la solution existe
        if(minimum==data.getDestination()) {
        	//On retrouve les sommets qui ont mené à la solution (dans le sens inverse malheureusement)
        	ArrayList<Node> predecesseurs=new ArrayList<Node>();
        	Node currentPred=data.getDestination();
        	predecesseurs.add(currentPred);
        	while(currentPred!=data.getOrigin()) { //on boucle tant qu'on n'a pas rajouté l'origine
        		predecesseurs.add(couts.get(currentPred.getId()).getNode());
        		currentPred=couts.get(currentPred.getId()).getNode();
        	}
        	
        	List<Node> noeudsChemin=null;
        	
        	for(int i=0;i<predecesseurs.size();i++) {
        		noeudsChemin.add(predecesseurs.get(predecesseurs.size()-i));
        	}
        	
        	Path chemin=new Path(graph);
        	chemin=chemin.createShortestPathFromNodes(graph,noeudsChemin);
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin);
        }
        //sinon on renvoie 
        else {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        return solution;
    }

}
