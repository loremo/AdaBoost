package classifier;

import java.util.Scanner;

import classifier.DTC.DecisionTreeClassifier;
import classifier.NBC.NaiveBayesClassifier;

public class ClassifierFactory {

	public static IClassifier createClassifier(String[] args, int i)
			throws Exception {
		IClassifier classifier = null;
		int numberOfHypothesis = Integer.parseInt(args[i++]);
		if (args[i].trim().toUpperCase().equals("NBC")) {
			classifier = new NaiveBayesClassifier();
		} else if (args[i].trim().toUpperCase().equals("DTC")) {
			int depth = -1;
			try {
				depth = Integer.parseInt(args[++i]);
			} catch (Exception e) {
				// default depth = -1
			}
			classifier = new DecisionTreeClassifier(depth);
		}
		classifier.setNoOfHypotheses(numberOfHypothesis);
		return classifier;
	}

	public static IClassifier createClassifier(Scanner scanner)
			throws Exception {
		while (true) {
			System.out
					.println("Choose at least one classifier:\nNBC (Naive Bayes Classifier)\nDTC (Decisin Tree Clasifier)");
			System.out.println("Q if you are done.");
			String classifierString = scanner.next().trim().toUpperCase();
			String secondArg = "";
			if (classifierString.trim().toUpperCase().equals("Q")) {
				return null;
			} else if (classifierString.equals("DTC")) {
				System.out.println("Tree depth? (a for max possible depth)");
				secondArg = scanner.next();
			}
			System.out.println("How many hypotheses?");
			String numberOfHypotheses = scanner.next().trim().toUpperCase();
			String[] arg = { numberOfHypotheses, classifierString, secondArg };
			IClassifier classifier = null;
			try {
				classifier = createClassifier(arg, 0);
			} catch (Exception e) {
				System.out.println("Wrong input.");
				continue;
			}
			return classifier;
		}
	}

}
