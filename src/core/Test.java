package core;

import classifier.IClassifier;
import classifier.IHypothese;
import data.Data;
import data.Instance;
import data.Result;

public class Test {

	GlobalSettings settings = new GlobalSettings();

	public Test(GlobalSettings settings) {
		this.settings = settings;
	}

	public Result test(Data data) {
		Result result = new Result();
		for (Instance feature : data.getTestData()) {
			for (IClassifier classifier : settings.getClassifiers()) {
				for (IHypothese hypotheses : classifier.getHypotheses()) {
					result.add(hypotheses.evaluate(feature));
				}
			}
		}
		return result;
	}

}
