package dna;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DNA {

	public static double mutation_rate = 0.01;

	private List<Double> genes;

	public DNA(List<Double> genes) {
		this.genes = new ArrayList<Double>();
		for (double gene : genes) {
			if(gene < 0)
				this.genes.add(0d);
			else if (gene > 1)
				this.genes.add(1d);
			else
				this.genes.add(gene);
		}
	}

	public DNA(int genesNumber,double arr[]) {
		this.genes = new ArrayList<Double>();

		for (int i = 0; i < genesNumber; i++) {
			this.genes.add(arr[i]);
		}
	}

	public DNA crossover(DNA partner) {
		List<Double> newGenes = new ArrayList<Double>();
		int partnerLength = partner.length();
		int mid = this.length() / 2;
		double rand = Math.random();
		for (int i = 0; i < genes.size(); i++) {
			if(rand < 0.5) {
				if (i < mid && i < partnerLength) 
					newGenes.add(partner.genes.get(i));
				else 
					newGenes.add(this.genes.get(i));
			} else {
				if (i < mid || i >= partnerLength) 
					newGenes.add(this.genes.get(i));
				else 
					newGenes.add(partner.genes.get(i));
			}
			
		}
		return new DNA(newGenes);
	}

	public void mutate() {
		for (int i = 0; i < this.genes.size(); i++) {
			if (Math.random() < mutation_rate) {
				this.genes.set(i, Math.random());
			}
		}
	}
	
	public double fitness() {
		double sum = 0;
		for(double gene : genes) {
			sum += gene;
		}
		
		double fitness = sum/genes.size();
		
		if(fitness > 0.5) {
			fitness /= 1+genes.get(0);
		}
		
		return fitness;
	}
	
	public List<Double> getGenes() {
		return this.genes;
	}
	
	public int length() {
		return this.genes.size();
	}
	
	public double getGene(int i) {
		return this.genes.get(i);
	}

	@Override
	public String toString() {

		DecimalFormat formatter = new DecimalFormat("#0.00");
		StringBuilder sb = new StringBuilder();

		sb.append("[");

		for (int i = 0; i < genes.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(formatter.format(genes.get(i)));
		}

		sb.append("]");

		return sb.toString();

	}
}
