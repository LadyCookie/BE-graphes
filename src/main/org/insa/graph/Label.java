package org.insa.graph;

public class Label implements Comparable<Label>{
	
	private double etiquette;
	
	private Node node;
	
	
	public Label(double etiquette, Node node) {
		this.etiquette=etiquette;
		this.node=node;
	}

	
	public double getLabel() {
		return this.etiquette;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public void setLabel(double etiquette) {
		this.etiquette=etiquette;
	}
	
	public int compareTo(Label autre) { //renvoie un entier positif si this > autre
		return (int)(this.etiquette-autre.getLabel());
	}
	
	public boolean compareNode(Label autre) {
		return(this.node.getId()==autre.getNode().getId());
	}
	
}
