package core;

import java.util.ArrayList;

import classifier.IClassifier;

public class GlobalSettings {

	private int percentage = 80;
	private String filepath = "./test.csv";
	private ArrayList<IClassifier> classifier = new ArrayList<IClassifier>();

	public int getPercentage() {
		return percentage;
	}

	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public ArrayList<IClassifier> getClassifier() {
		return classifier;
	}

	public void setClassifier(ArrayList<IClassifier> classifier) {
		this.classifier = classifier;
	}
}
