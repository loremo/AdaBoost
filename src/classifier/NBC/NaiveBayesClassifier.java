package classifier.NBC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import classifier.IClassifier;
import classifier.IHypothesis;
import data.Data;
import data.Instance;

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

		// <Feature, <Value, <Label, Weight>>>
		HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>> conditionalProbabilities = new HashMap<Integer, HashMap<Integer, HashMap<Integer, Double>>>();
		// compute conditional probabilities
		for (Integer label : labels) {
			List<Instance> instancesWithSameLabel = data.getTrainData().get(label);
			for (Instance instance : instancesWithSameLabel) {
				for (int i = 0; i < instance.getFeatures().size(); i++) {
					if (!conditionalProbabilities.containsKey(i)) { // no such feature
						HashMap<Integer, HashMap<Integer, Double>> value_labels = new HashMap<Integer, HashMap<Integer, Double>>();
						HashMap<Integer, Double> label_weights = new HashMap<Integer, Double>();
						label_weights.put(label, instance.getWeight());
						value_labels.put(instance.getFeatures().get(i), label_weights);
						conditionalProbabilities.put(i, value_labels);
					} else {
						if (!conditionalProbabilities.get(i).containsKey(instance.getFeatures().get(i))) { // no such value
							HashMap<Integer, Double> label_weights = new HashMap<Integer, Double>();
							label_weights.put(label, instance.getWeight());
							conditionalProbabilities.get(i).put(instance.getFeatures().get(i), label_weights);
						} else {
							if (!conditionalProbabilities.get(i).get(instance.getFeatures().get(i)).containsKey(label)) { // no such label
								conditionalProbabilities.get(i).get(instance.getFeatures().get(i)).put(label, instance.getWeight());
							} else { // the needed entry exists, only an update of weight is needed
								double currentWeight = conditionalProbabilities.get(i).get(instance.getFeatures().get(i)).get(label);
								currentWeight += instance.getWeight();
								conditionalProbabilities.get(i).get(instance.getFeatures().get(i)).put(label, currentWeight);
							}
						}
					}
				}
			}
		}
		Set<Integer> features = conditionalProbabilities.keySet();
		for (Integer feature : features) {
			Set<Integer> values = conditionalProbabilities.get(feature).keySet();
			for (Integer value : values) {
				Set<Integer> labelsProbabilities = conditionalProbabilities.get(feature).get(value).keySet();
				for (Integer labelsProbability : labelsProbabilities) {
					double conditionalProbability = conditionalProbabilities.get(feature).get(value).get(labelsProbability)
							/ aprioriProbabilities.get(labelsProbability);
					conditionalProbabilities.get(feature).get(value).put(labelsProbability, conditionalProbability);
				}

			}
		}
		NaiveBayesHypothesis hypothesis = new NaiveBayesHypothesis();
		hypothesis.setConditionalProbabilities(conditionalProbabilities);
		hypothesis.setAprioriProbabilities(aprioriProbabilities);
		hypothesis.setLabels(labels);
		this.hypotheses.add(hypothesis);
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
