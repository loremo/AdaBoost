package classifier;

import java.io.IOException;

import org.junit.Test;

import classifier.NBC.NaiveBayesClassifier;
import core.GlobalSettings;
import data.Data;
import data.Instance;
import data.preprocessing.Preprocessor;


public class NaiveBayesClassifierTest {
	
	@Test
	public void testHypothese() throws IOException {
		GlobalSettings settings = new GlobalSettings();
		settings.setFilepath("/home/dimko/Dropbox/Uni/AI/AdaBoost/data/test/testCase1.txt");
		settings.setPercentage(100);
		Preprocessor preprocessor = new Preprocessor(settings);
		Data data = preprocessor.getData();
		data.getTrainData().get(0).get(0).setWeight(0.15);
		data.getTrainData().get(0).get(1).setWeight(0.25);
		data.getTrainData().get(1).get(0).setWeight(0.4);
		data.getTrainData().get(1).get(1).setWeight(0.1);
		data.getTrainData().get(1).get(2).setWeight(0.1);
		NaiveBayesClassifier nbc = new NaiveBayesClassifier();
		nbc.generateHypothese(data);
		Instance testInstance = new Instance();
		testInstance.addFeature(0);
		testInstance.addFeature(2);
		testInstance.addFeature(0);
		int testLabel = nbc.getHypotheses().get(0).evaluate(testInstance);
		System.out.println(testLabel);
	}

}
