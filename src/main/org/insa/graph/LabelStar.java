package org.insa.graph;

public class LabelStar extends Label{

	protected float coutEstime;
	
	public LabelStar (double etiquette, Node nodeCourant,Node nodePere, boolean marque, float cost) {
		super(etiquette,nodeCourant,nodePere,marque);
		this.coutEstime=cost;
	}
	
	public float getCost() {
		return this.coutEstime;
	}
	
	public void setCost(float cost) {
		this.coutEstime=cost;
	}
	
	public int compareTo(LabelStar autre) { //renvoie un entier positif si this > autre
		return (int)(this.etiquette+this.coutEstime-autre.getEtiquette()-autre.getCost());
	}
	
	
}
