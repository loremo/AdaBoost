package classifier.NBC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import classifier.IClassifier;
import classifier.IHypothesis;
import data.Data;
import data.Instance;
import data.preprocessing.DataDiscretizer;

public class NaiveBayesClassifier implements IClassifier {

	ArrayList<IHypothesis> hypotheses = new ArrayList<IHypothesis>();
	int numberOfHypotheses = -1;

	@Override
	public void setNoOfHypotheses(int number) {
		this.numberOfHypotheses = number;
	}

	@Override
	public int getNoOfHypotheses() {
		return numberOfHypotheses;
	}

	@Override
	public void generateHypothese(Data data) {
		// <label, probability>
		HashMap<Integer, Double> aprioriProbabilities = computeAprioriProbabilities(data);
		
		// [label][feature][value]
		double[][][] conditionalProbabilities = computeConditionalProbabilites(data, aprioriProbabilities);;

		NaiveBayesHypothesis hypothesis = new NaiveBayesHypothesis();
		hypothesis.setConditionalProbabilities(conditionalProbabilities);
		hypothesis.setAprioriProbabilities(aprioriProbabilities);
		Set<Integer> labels = data.getTrainData().keySet();
		hypothesis.setLabels(labels);
		this.hypotheses.add(hypothesis);
	}

	private double[][][] computeConditionalProbabilites(Data data, HashMap<Integer, Double> aprioriProbabilities) {
		double[][][] conProbs = new double[Collections.max(data.getTrainData().keySet()) + 1][data.getNumberOfFeatures()][DataDiscretizer.NUMBEROFINTERVALS];
		
		Set<Integer> labels = data.getTrainData().keySet();
		// compute conditional probabilities
		for (Integer label : labels) {
			List<Instance> instancesWithSameLabel = data.getTrainData().get(label);
			for (Instance instance : instancesWithSameLabel) {
				for (int i = 0; i < instance.getFeatures().size(); i++) {
					conProbs[label][i][instance.getFeatures().get(i)] += instance.getWeight();
				}
			}
		}

		for (Integer label : labels) {
			for (int i = 0; i < conProbs[0].length; i++) {
				for (int j = 0; j < conProbs[0][0].length; j++) {
					conProbs[label][i][j] /= aprioriProbabilities.get(label);
				}
			}
		}
		return conProbs;
	}

	private HashMap<Integer, Double> computeAprioriProbabilities(Data data) {
		HashMap<Integer, Double> aprioriProbabilities = new HashMap<Integer, Double>();

		Set<Integer> labels = data.getTrainData().keySet();
		// compute apriori probabilities for each label
		for (Integer label : labels) {
			List<Instance> instancesWithSameLabel = data.getTrainData().get(label);
			double apriori = 0;
			for (Instance instance : instancesWithSameLabel) {
				apriori += instance.getWeight();
			}
			aprioriProbabilities.put(label, apriori);
		}
		return aprioriProbabilities;

	}

	@Override
	public ArrayList<IHypothesis> getHypotheses() {
		return hypotheses;
	}

	@Override
	public String toString() {
		return "NBC" + " | Number of hypotheses: " + numberOfHypotheses;
	}
}
