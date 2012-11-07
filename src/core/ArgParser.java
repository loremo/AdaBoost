package core;

import java.util.Scanner;

import classifier.ClassifierFactory;
import classifier.IClassifier;

public class ArgParser {

	public static GlobalSettings parseArgs(String[] argss) {
		String[] args = {"-f", "bla", "-p", "80", "-c", "10", "NBC", "-c", "10", "DTC", "10"};
		if (args.length == 0) {
			return interactiveArgs();
		}
		String errorMessage = "Usage: -f FILE -p PROCENTAGE -c NUMBEROFHYPOTHESIS CLASSIFIER [ARG]\n";
		errorMessage += "where CLASSIFIER can be:\n";
		errorMessage += "\tNBC\t(Naive Bayes Classifier)\n";
		errorMessage += "\tDTC N\t(Decision Tree Classifier with depth N[default: max depth])\n";
		GlobalSettings settings = new GlobalSettings();
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].trim().equals("-f")) {
					settings.setFilepath(args[++i]);
				} else if (args[i].trim().equals("-p")) {
					settings.setPercentage(Integer.parseInt(args[++i]));
				} else if (args[i].trim().equals("-c")) {
					settings.addClassifier(ClassifierFactory.createClassifier(
							args, ++i));
				}
			}
			if (settings.getClassifiers().isEmpty()
					|| settings.getFilepath().isEmpty()) {
				throw new Exception();
			}
		} catch (Exception e) {
			System.out.println(errorMessage);
			System.exit(1);
		}
		return settings;
	}

	private static GlobalSettings interactiveArgs() {

		GlobalSettings settings = new GlobalSettings();

		Scanner scanner = new Scanner(System.in);

		// TODO: file-wizard or list of files in a data-folder
		// TODO: check if file exists
		System.out.println("Filename?");
		settings.setFilepath(scanner.next());

		int procentage = 80;
		while (true) {
			System.out.println("How many % of data for training?");
			try {
				procentage = scanner.nextInt();
			} catch (Exception e) {
				scanner.next();
				continue;
			}
			if (procentage > 0 && procentage < 100) {
				break;
			} else {
				continue;
			}
		}
		settings.setPercentage(procentage);

		while (true) {
			IClassifier classifier = null;
			try {
				classifier = ClassifierFactory.createClassifier(scanner);				
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (classifier == null && !settings.getClassifiers().isEmpty()) {
				break;
			}
			settings.addClassifier(classifier);
		}
		return settings;
	}
}
