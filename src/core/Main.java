package core;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

import data.Data;
import data.preprocessing.Preprocessor;

public class Main {

	public static void main(String[] argss) throws IOException {

		String file = "data/waveform.data";

		ArrayList<String[]> argsList = new ArrayList<String[]>();
		String[] args1 = { "-f", file, "-p", "80", "-c", "1", "nbc" };
		argsList.add(args1);
		String[] args2 = { "-f", file, "-p", "80", "-c", "1", "dtc", "-1" };
		argsList.add(args2);
		String[] args3 = { "-f", file, "-p", "80", "-c", "5", "nbc" };
		argsList.add(args3);
		String[] args4 = { "-f", file, "-p", "80", "-c", "10", "nbc" };
		argsList.add(args4);
		String[] args5 = { "-f", file, "-p", "80", "-c", "20", "nbc" };
		argsList.add(args5);
		String[] args6 = { "-f", file, "-p", "80", "-c", "5", "dtc", "-1" };
		argsList.add(args6);
		String[] args7 = { "-f", file, "-p", "80", "-c", "10", "dtc", "1" };
		argsList.add(args7);
		String[] args8 = { "-f", file, "-p", "80", "-c", "10", "dtc", "2" };
		argsList.add(args8);
		String[] args9 = { "-f", file, "-p", "80", "-c", "10", "dtc", "-1" };
		argsList.add(args9);
		String[] args10 = { "-f", file, "-p", "80", "-c", "20", "dtc", "-1" };
		argsList.add(args10);
		String[] args11 = { "-f", file, "-p", "80", "-c", "5", "nbc", "-c", "5", "dtc", "2" };
		argsList.add(args11);
		String[] args12 = { "-f", file, "-p", "80", "-c", "10", "nbc", "-c", "10", "dtc", "2" };
		argsList.add(args12);
		String[] args13 = { "-f", file, "-p", "80", "-c", "20", "nbc", "-c", "20", "dtc", "2" };
		argsList.add(args13);

		for (String[] args : argsList) {

			// String[] args = { "-f", "data/yeast.txt", "-p", "80", "-c", "3", "nbc", "2"};

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

//			System.out.println(settings.getFilepath());
////			System.out.println("number of features: " + data.getNumberOfFeatures());
////			System.out.println("test instances: " + data.getTestData().size());
////			System.out.println("Number of classes: " + data.getTrainData().keySet().size());
//			System.out.println(trainingResult);
//			System.out.println("Test accuracy: " + testResult);
//			System.out.println();
			
			// Output for latex-table
			String s = "";
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			DecimalFormat douF = new DecimalFormat("##.#", otherSymbols);
			for (int i = 0; i < settings.getClassifiers().size(); i++) {
				s += settings.getClassifiers().get(i).toStringShort();
				if(i < settings.getClassifiers().size() - 1) {
					s += "+";
				}
			}
			s += " & " + settings.getClassifiers().get(0).getNoOfHypotheses();
			s += " &  "; // missing max depth
			s += " & ";
			for (int i = 0; i < trainingResult.getAverages().size(); i++) {
				s += douF.format(trainingResult.getAverages().get(i) * 100);
				if(i < settings.getClassifiers().size() - 1) {
					s += " / ";
				}
			}
			s += " & ";
			for (int i = 0; i < trainingResult.getDeviations().size(); i++) {
				s += douF.format(trainingResult.getDeviations().get(i) * 100);
				if(i < settings.getClassifiers().size() - 1) {
					s += " / ";
				}
			}
			s += " & " + douF.format(testResult * 100);
			s += " \\\\";
			System.out.println(s);
			
		}
		System.out.println();
	}

}
