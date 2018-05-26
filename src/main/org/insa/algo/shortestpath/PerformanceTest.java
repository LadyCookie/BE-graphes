package org.insa.algo.shortestpath;

import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Random;

public class PerformanceTest {
	private static int nombreTest;
    private static Graph carte;

    private static Random rand;

    private static int distancemin;
    private static int distancemax;
    
    private static Node Origin;
    private static Node Destination;

    private static String separateur;


    private static PrintWriter writer;
 //   private static double delta;

    @BeforeClass
    public static void initAll() throws Exception {
        nombreTest=100; //nombre de tests � faire
        
        //bornes de la longueur du chemin en km
        distancemin=0;
        distancemax=100;

        separateur=";"; //s�parateur pour le format csv
        rand=new Random(); //variable random
        
        //Extraction de la carte
        String mapName="C:\\Users\\Const\\Documents\\BE_Graph\\Maps\\bretagne.mapgr";
        GraphReader reader=new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
        carte=reader.read();

        //cr�ation du fichier
        writer = new PrintWriter("C:\\Users\\Const\\Documents\\BE_Graph\\Sortie\\"+carte.getMapName()+distancemin+"-"+distancemax+".csv", "UTF-8");
       //�criture des premi�res colonnes
        writer.println("Type Algo"+separateur+"IdOrigine"+separateur+"IdDestination"+separateur+"Distance(m)"+separateur+"NbNodesChemin"+separateur+"Temps(en ms)"+separateur+"nombreSommetVisites");
    }

    @Test
    public void generateTest(){
        int i=0;
        while (i<nombreTest){
        //tant qu'on a pas fait le nombre de tests qu'on veut
            //avancement selon le nombre de test que l'on veut effectuer
            System.out.println("Avancement du test :"+(((double)i/(double)nombreTest)*100.0)+"%");
            //on prend une origine est une destination au hasard
            Origin=carte.get(rand.nextInt(carte.size()-1));
            Destination=carte.get(rand.nextInt(carte.size()-1));
            
            //si le Dijktra fonctionne on lance le AStar
            if (Dijkstra(Origin,Destination) && AStar(Origin,Destination)){
                i+=1;
            }
        }
        writer.close();
    }

    public boolean Dijkstra(Node ori, Node dest){
        boolean retour=false;
        //pour construire ce que l'on veut �crire dans le doc
        StringBuilder ret=new StringBuilder();
        
        //on initialise le Dijkstra
        ShortestPathData data=new ShortestPathData(carte,ori,dest,ArcInspectorFactory.getAllFilters().get(0));
        ShortestPathAlgorithm dijkstraAlgo=new DijkstraAlgorithm(data);
        
        //on note le temps de d�but
        long debut=System.nanoTime();
        
        //on fait tourner le Dijstra
        ShortestPathSolution solution=dijkstraAlgo.doRun();
        long end=System.nanoTime();
        //on note le temps que �a a prit
        double time=((double)(end-debut)/1000000.0);
        
        //si le chemin est valide et qu'il est bien dans les bornes
        if ((solution.isFeasible())&&(solution.getPath().getLength()<(float)distancemax*1000.0) && (solution.getPath().getLength()> (float)distancemin*1000.0)){
            System.out.println("Chemin trouv�");
            int size=solution.getPath().size()+1;
            
            //�criture dans le document
            ret.append("dijkstra"+separateur+ori.getId()+separateur+dest.getId()+separateur);
            ret.append(solution.getPath().getLength()+separateur+size+separateur+time+separateur+solution.getNombreSommetVisite());
            writer.println(ret.toString().replaceAll("\\.",","));
            
            retour=true;
        }
        return retour;
    }

    public boolean AStar(Node ori,Node dest){
        Boolean retour=false;
        StringBuilder ret=new StringBuilder();
        
        ShortestPathData data=new ShortestPathData(carte,ori,dest,ArcInspectorFactory.getAllFilters().get(0));
        ShortestPathAlgorithm aStarAlgo=new AStarAlgorithm(data);
        
        long debut=System.nanoTime();
        ShortestPathSolution solution=aStarAlgo.doRun();
        long end=System.nanoTime();
        double time=((double)(end-debut)/1000000.0);
        
        if ((solution.isFeasible())&&(solution.getPath().getLength()<(float) distancemax*1000.0) && (solution.getPath().getLength()> (float) distancemin*1000.0)){
        	ret.append("astar"+separateur+ori.getId()+separateur+dest.getId()+separateur);
        	ret.append(solution.getPath().getLength()+separateur+solution.getPath().getArcs().size()+1+separateur+time+separateur+solution.getNombreSommetVisite());
            writer.println(ret.toString().replaceAll("\\.",","));
            retour=true;
        }
        return retour;
    }
}
