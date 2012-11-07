package core;

import classifier.IClassifier;
import data.Data;

public class Training {

	GlobalSettings settings = new GlobalSettings();

	public Training(GlobalSettings settings) {
		this.settings = settings;
	}

	public void train(Data data) {
		for (IClassifier classifier : settings.getClassifiers()) {
			for (int i = 0; i < classifier.getNoOfHypotheses(); i++) {
				classifier.generateHypothese();
				updateWeights(); // TODO parameters and return type?
			}
		}
	}

	private void updateWeights() {
		// TODO do it		
	}

}
