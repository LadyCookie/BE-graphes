package org.insa.graph;

public class Label implements Comparable<Label>{
	
	private double etiquette;
	
	private Node nodeCourant;
	
	private Node nodePere;
	
	private boolean marque;
	
	public Label(double etiquette, Node nodeCourant,Node nodePere, boolean marque) {
		this.etiquette=etiquette;
		this.nodeCourant=nodeCourant;
		this.nodePere=nodePere;
		this.marque=marque;
	}

	
	public double getEtiquette() {
		return this.etiquette;
	}
	
	public Node getNodeCourant() {
		return this.nodeCourant;
	}
	
	public Node getNodePere() {
		return this.nodePere;
	}
	
	public boolean getMarque() {
		return this.marque;
	}
	
	
	
	public void setEtiquette(double etiquette) {
		this.etiquette=etiquette;
	}
	
	public void setNodeCourant(Node node) {
		this.nodeCourant=node;
	}
	
	public void setNodePere(Node node) {
		this.nodePere=node;
	}
	
	public void setMarque(boolean marque) {
		this.marque=marque;
	}
	
	
	
	public int compareTo(Label autre) { //renvoie un entier positif si this > autre
		return (int)(this.etiquette-autre.getEtiquette());
	}
	
	/*public boolean compareNode(Label autre) {
		return(this.node.getId()==autre.getNode().getId());
	}*/
	
}
