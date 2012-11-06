package data;

import java.util.ArrayList;

public class Data {
	private ArrayList<Feature> trainFeatures = new ArrayList<Feature>();
	private ArrayList<Feature> testFeatures = new ArrayList<Feature>();

	public ArrayList<Feature> getTrainFeatures() {
		return trainFeatures;
	}

	public void setTrainFeatures(ArrayList<Feature> trainFeatures) {
		this.trainFeatures = trainFeatures;
	}

	public ArrayList<Feature> getTestFeatures() {
		return testFeatures;
	}

	public void setTestFeatures(ArrayList<Feature> testFeatures) {
		this.testFeatures = testFeatures;
	}
}
