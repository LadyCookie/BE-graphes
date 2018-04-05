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
	
	public int compareTo(Label autre) { //renvoie un entier positif si this > autre
		return (int)(this.etiquette-autre.getLabel());
	}
	
}
