package core;

import java.util.ArrayList;

import classifier.IClassifier;

public class GlobalSettings {

	private int percentage = 80;
	private String filepath = "";
	private ArrayList<IClassifier> classifiers = new ArrayList<IClassifier>();

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

	public ArrayList<IClassifier> getClassifiers() {
		return classifiers;
	}

	public void addClassifier(IClassifier classifier) {
		classifiers.add(classifier);
	}
	
	@Override
	public String toString() {
		String s = filepath + " | " + percentage + "%\n";
		for (IClassifier classifier : classifiers) {
			s += classifier.toString() + "\n";
		}
		return s;
	}
}
