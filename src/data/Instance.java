package data;

import java.util.ArrayList;

public class Instance {
	private ArrayList<Integer> features = new ArrayList<Integer>();
	private int label;
	private double weight;

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public ArrayList<Integer> getFeatures() {
		return features;
	}

	public void addFeature(int value) {
		features.add(value);
	}
}
