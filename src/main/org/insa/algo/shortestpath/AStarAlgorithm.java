package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Iterator;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

    
    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph();
        int NbNoeudMarque=0;
        
        
        ArrayList<Label> couts=new ArrayList<Label>(graph.size());

        //initialiser les couts
        for(int i=0;i<graph.size();i++) {
        	couts.add(null);
        }
        
        
        BinaryHeap<Label> file_traitement=new BinaryHeap<Label>();
        
        //initialisation des couts
        
        Node minimum=data.getOrigin();
        Label currentL=new LabelStar(0,minimum,null,true,0);
        couts.set(minimum.getId(),currentL);
        
        
        boolean vide=(data.getOrigin()==data.getDestination());
        //à la premi�re iteration, minimum est egal � l'origine sauf si l'origine est la destination
        
        while(minimum!=data.getDestination() && !vide) { //soit on s'apprêter à traiter la destination(inutile) soit on a déjà tout parcouru    
        	Node currentNode=currentL.getNodeCourant(); //On récupère le noeud(père) sur lequel on va travailler
        	
        	Iterator<Arc> successeur=currentNode.iterator(); //On récupère les arcs vers les fils de currentNode     	
        	
        	
        	while(successeur.hasNext()) { //On parcourt les successeurs de currentNode
        		Arc arcVersFils=successeur.next(); 
        		//on calcule le PPC à partir du noeud actuel
        		double coutActuel=currentL.getEtiquette()+arcVersFils.getLength();
        		
        		
        		//On compare au cout jusqu'alors enregistré, Si newLabel plus court, on actualise le cout
        		if(couts.get(arcVersFils.getDestination().getId())==null) { //si le noeud n'a pas encore d'étiquette, il faut l'initialiser dans le tableau
        			couts.set(arcVersFils.getDestination().getId(),new LabelStar(coutActuel,arcVersFils.getDestination(),currentNode,false,arcVersFils.getDestination().getPoint().distanceTo(data.getDestination().getPoint())));
        			file_traitement.insert(couts.get(arcVersFils.getDestination().getId()));
        		}
        		else{ 
        			
        			if(couts.get(arcVersFils.getDestination().getId()).getEtiquette() > coutActuel && !couts.get(arcVersFils.getDestination().getId()).getMarque()) { 
        				file_traitement.remove(couts.get(arcVersFils.getDestination().getId())); //Pour éviter d'avoir un même noeud 2fois     	
        				couts.get(arcVersFils.getDestination().getId()).setEtiquette(coutActuel);
                		couts.get(arcVersFils.getDestination().getId()).setNodePere(currentNode);
                		file_traitement.insert(couts.get(arcVersFils.getDestination().getId()));
        			}
        		}
        		
        	}
        	
        	vide=file_traitement.isEmpty();
        	
        	if(!vide) {
        		currentL=file_traitement.deleteMin(); //On cherche le noeud à traiter en priorité et on l'enlève du tas, ce qui équivaut à marquer le sommet
        		minimum=currentL.getNodeCourant(); //Permet de voir si on traite la destination (ie fin de l'algorithme)
        		couts.get(minimum.getId()).setMarque(true);
        		notifyNodeReached(currentL.getNodeCourant());
        		currentL.setMarque(true);
        		NbNoeudMarque++;
        	}
        }
        
        //Dans le cas où la solution existe
        if(!(data.getOrigin()==data.getDestination()) && minimum==data.getDestination()) {
        	//On retrouve les sommets qui ont mené à la solution (dans le sens inverse malheureusement)
        	
        	ArrayList<Node> predecesseurs=new ArrayList<Node>();
        	
        	Node currentPred=data.getDestination();
        	predecesseurs.add(currentPred);
        	while(currentPred!=data.getOrigin()) { //on boucle tant qu'on n'a pas rajouté l'origine
        		predecesseurs.add(couts.get(currentPred.getId()).getNodePere());
        		currentPred=couts.get(currentPred.getId()).getNodePere();
        	}
        	
        	ArrayList<Node> noeudsChemin=new ArrayList<Node>();
        	
        	for(int i=0;i<predecesseurs.size();i++) {
        		noeudsChemin.add(predecesseurs.get(predecesseurs.size()-(i+1)));
        	}
        	
        	Path chemin=new Path(graph);
        	chemin=Path.createShortestPathFromNodes(graph,noeudsChemin);

        	solution = new ShortestPathSolution(data, Status.OPTIMAL, chemin,NbNoeudMarque);
        }
        //sinon on renvoie 
        else {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        return solution;
    }

    
}

