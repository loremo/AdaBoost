package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Data {
	private HashMap<Integer, List<Instance>> trainData = new HashMap<Integer, List<Instance>>();
	private List<Instance> testData = new ArrayList<Instance>();
	
	HashMap<Integer, ArrayList<Integer>> featureSubClasses = null;

	private int numberOfFeatures;

	public HashMap<Integer, List<Instance>>  getTrainData() {
		return trainData;
	}

	public void setTrainData(HashMap<Integer, List<Instance>>  trainData) {
		this.trainData = trainData;
	}

	public List<Instance> getTestData() {
		return testData;
	}

	public void setTestData(List<Instance> testData) {
		this.testData = testData;
	}
	
	public ArrayList<Instance> getListOfTrainInstances(){
		ArrayList<Instance> trainList = new ArrayList<Instance>();
		Set<Integer> labels = trainData.keySet();
		for (Integer label : labels) {
			trainList.addAll(trainData.get(label));
		}
		return trainList;
	}

	public HashMap<Integer, ArrayList<Integer>> numberOfFeatureSubClasses() {
		if (featureSubClasses == null) {
			featureSubClasses = new HashMap<Integer, ArrayList<Integer>>();

			Set<Integer> keys = trainData.keySet();
			for (Integer label : keys) {
				List<Instance> instancesWIthSameLabel = trainData.get(label);
				for (Instance instance : instancesWIthSameLabel) {
					for (int i = 0; i < instance.getFeatures().size(); i++) {
						if (featureSubClasses.containsKey(i)) {
							if (!featureSubClasses.get(i).contains(instance.getFeatures().get(i))) {
								featureSubClasses.get(i).add(instance.getFeatures().get(i));
							}
						}
					}
				}
			}
		}
		return featureSubClasses;
	}
	
	public int getNumberOfFeatures() {
		return numberOfFeatures;
	}

	public void setNumberOfFeatures(int numberOfFeatures) {
		this.numberOfFeatures = numberOfFeatures;
	}
}
