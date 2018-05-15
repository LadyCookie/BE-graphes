package org.insa.graph;

public class LabelStar extends Label{

	protected double coutEstime;
	
	public LabelStar (double etiquette, Node nodeCourant,Node nodePere, boolean marque, double cost) {
		super(etiquette,nodeCourant,nodePere,marque);
		this.coutEstime=cost;
	}
	
	public double getCost() {
		return this.coutEstime;
	}
	
	public void setCost(double cost) {
		this.coutEstime=cost;
	}
	
	public int compareTo(LabelStar autre) { //renvoie un entier positif si this > autre
			
		int result= (int)(this.etiquette+this.coutEstime-autre.getEtiquette()-autre.getCost());
		if(result==0) {
			result=(int)(this.coutEstime-autre.getCost());
		}
		return result;
	}
	
	
}
