package data;

import java.util.ArrayList;
import java.util.List;

public class Data {
	private List<Instance> trainData = new ArrayList<Instance>();
	private List<Instance> testData = new ArrayList<Instance>();

	public List<Instance> getTrainData() {
		return trainData;
	}

	public void setTrainData(List<Instance> trainData) {
		this.trainData = trainData;
	}

	public List<Instance> getTestData() {
		return testData;
	}

	public void setTestData(List<Instance> testData) {
		this.testData = testData;
	}
}
