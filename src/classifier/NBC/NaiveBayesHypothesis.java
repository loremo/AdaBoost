package classifier.NBC;

import java.util.HashMap;
import java.util.Set;

import classifier.IHypothesis;
import data.Instance;

public class NaiveBayesHypothesis implements IHypothesis {

	// <Attribute, <Feature, <Label, Weight>>>
	HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> conditionalProbabilities = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
	HashMap<Integer, Double> aprioriProbabilities = null;
	private double weight = 0;
	Set<Integer> labels;
	
	@Override
	public int evaluate(Instance instance) {
		// <Label, weighted votes>
		HashMap<Integer, Double> votesPerLabel = new HashMap<Integer, Double>();
		for (int i = 0; i < instance.getFeatures().size(); i++) {
			// <label, conditional probabilities>
			HashMap<Integer, Double> conProbPerLabel = conditionalProbabilities.get(i).get(instance.getFeatures().get(i));
			if (conProbPerLabel != null) {
				for (Integer label : labels) {
					double probability = votesPerLabel.get(label) == null ? 1.0 : votesPerLabel.get(label);
					if (conProbPerLabel.containsKey(label)) {
						probability *= conProbPerLabel.get(label);
					} else {
						probability = 0;
					}
					votesPerLabel.put(label, probability);
				}
			} 
		}
		Set<Integer> labels = votesPerLabel.keySet();
		double maxProbability = Double.NEGATIVE_INFINITY;
		int winner = -1;
		for (Integer label : labels) {
			votesPerLabel.put(label, votesPerLabel.get(label) * aprioriProbabilities.get(label));
			if (votesPerLabel.get(label) > maxProbability) {
				maxProbability = votesPerLabel.get(label);
				winner = label;
			}
		}
		return winner;
	}

	public void setConditionalProbabilities(HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> conditionalProbabilities) {
		this.conditionalProbabilities = conditionalProbabilities;
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

}
