package core;

import java.io.IOException;

import classifier.IClassifier;
import classifier.IHypothesis;
import data.Data;
import data.preprocessing.Preprocessor;

public class SingleMain {

	public static void main(String[] argss) throws IOException {

		String[] args = { "-f", "data/page-blocks.txt", "-p", "80", "-c", "1", "nbc", "2" };

		// parse arguments
		GlobalSettings settings = ArgParser.parseArgs(args);

		// preprocess data
		Preprocessor preprocessor = new Preprocessor(settings);
		Data data = preprocessor.getData();
		System.out.println("h-error:");
		// train classifiers
		Training training = new Training(settings);
		training.train(data);

		System.out.println("h-weights:");
		for (IClassifier classifier : settings.getClassifiers()) {
			for (IHypothesis hypothesis : classifier.getHypotheses()) {
				System.out.println(hypothesis.getWeight());
			}
		}
		System.out.println("accuracy on training data:");
		
		// train+test error
		Test test = new Test(settings);
		double testResult = test.testError(data.getTestData());
		TrainingResult trainingResult = test.trainError(data.getTrainData());
		
		System.out.println(settings.getFilepath());
		 System.out.println("number of features: " + data.getNumberOfFeatures());
		 System.out.println("test instances: " + data.getTestData().size());
		 System.out.println("Number of classes: " + data.getTrainData().keySet().size());
		System.out.println(trainingResult);
		System.out.println("Test accuracy: " + testResult);
		System.out.println();
	}

}
