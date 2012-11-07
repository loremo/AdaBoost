package core;

import classifier.IClassifier;
import classifier.IHypothese;
import data.Data;
import data.Feature;
import data.Result;

public class Test {

	GlobalSettings settings = new GlobalSettings();

	public Test(GlobalSettings settings) {
		this.settings = settings;
	}

	public Result test(Data data) {
		Result result = new Result();
		for (Feature feature : data.getTestFeatures()) {
			for (IClassifier classifier : settings.getClassifiers()) {
				for (IHypothese hypotheses : classifier.getHypotheses()) {
					result.add(hypotheses.evaluate(feature));
				}
			}
		}
		return result;
	}

}
