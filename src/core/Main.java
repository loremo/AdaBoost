package core;

import data.Data;
import data.Result;

public class Main {

	public static void main(String[] args) {
		// parse arguments
		GlobalSettings settings = ArgParser.parseArgs(args);

		// preprocess data
		Preprocessor preprocessor = new Preprocessor(settings);
		Data data = preprocessor.preprocessData();

		// train classifiers
		Training training = new Training(settings);
		training.train(data);

		// test classifiers
		Test test = new Test(settings);
		Result result = test.test(data);

		// analyse results
		Analysis analysis = new Analysis(settings);
		analysis.analyse(result);
		analysis.print();
	}

}
