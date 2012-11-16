package classifier.NBC;

import java.util.HashMap;
import java.util.Set;

import classifier.IHypothesis;
import data.Instance;

public class NaiveBayesHypothesis implements IHypothesis {

	HashMap<Integer, Double> aprioriProbabilities = null;
	// [label][feature][value]
	private double[][][] conditionalProbabilities;
	
	private double weight = 0;
	Set<Integer> labels;


	@Override
	public int evaluate(Instance instance) {
		double maxProb = Double.NEGATIVE_INFINITY;
		int winner = -1;
		for (Integer label : labels) {
			double labelProbability = 1;
			for (int i = 0; i < instance.getFeatures().size(); i++) {
				labelProbability *= conditionalProbabilities[label][i][instance.getFeatures().get(i)]; 
			}
			labelProbability *= aprioriProbabilities.get(label);
			if (labelProbability > maxProb) {
				maxProb = labelProbability;
				winner = label;
			}
		}
		return winner;
	}

	public void setLabels(Set<Integer> labels) {
		this.labels = labels;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;

	}

	public void setAprioriProbabilities(HashMap<Integer, Double> aprioriProbabilities) {
		this.aprioriProbabilities = aprioriProbabilities;
	}

	public void setConditionalProbabilities(double[][][] conditionalProbabilities) {
		this.conditionalProbabilities = conditionalProbabilities;
	}

}
