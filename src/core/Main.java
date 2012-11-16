package core;

import java.io.IOException;

import classifier.IClassifier;
import classifier.IHypothesis;
import data.Data;
import data.preprocessing.Preprocessor;

public class Main {

	public static void main(String[] argss) throws IOException {

		String[] args = { "-f", "data/nursery.txt", "-p", "80", "-c", "20", "nbc", "2"};

		// parse arguments
		GlobalSettings settings = ArgParser.parseArgs(args);

		// preprocess data
		Preprocessor preprocessor = new Preprocessor(settings);
		Data data = preprocessor.getData();
		

		// train classifiers
		Training training = new Training(settings);
		training.train(data);

		// train+test error
		Test test = new Test(settings);
		double testResult = test.testError(data.getTestData());
		TrainingResult trainingResult = test.trainError(data.getTrainData());

		for (IClassifier classifier : settings.getClassifiers()) {
			for (IHypothesis hypotheses : classifier.getHypotheses()) {
				System.out.println(hypotheses.getWeight());
			}
		}

		System.out.println(settings.getFilepath());
		System.out.println("number of features: " + data.getNumberOfFeatures());
		System.out.println("test instances: " + data.getTestData().size());
		System.out.println("Number of classes: " + data.getTrainData().keySet().size());
		System.out.println(trainingResult);
		System.out.println("Test accuracy: " + testResult);
	}

}
