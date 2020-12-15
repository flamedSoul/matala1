package ex1;

import java.io.Serializable;


public class Edge implements Serializable{	
	private double weight;
	private node_info inf;

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		Edge other = (Edge)obj;
		return other.weight == this.weight && other.getInf().getKey() == this.getInf().getKey();	
	}

	/**
	 * this constructor Initialize Edge fields
	 * @param weight
	 * @param inf
	 */
	public Edge(double weight, node_info inf) {

		this.weight = weight;
		this.inf = inf;
	}

	public double getWeight() {

		return weight;
	}

	public void setWeight(double weight) {

		this.weight = weight;
	}

	public node_info getInf() {

		return inf;
	}

	public void setInf(node_info inf) {

		this.inf = inf;
	}

}
