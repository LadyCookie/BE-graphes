package org.insa.graph;

public class Label {
	
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
	
}
