package classifier.DTC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import classifier.IClassifier;
import classifier.IHypothesis;
import data.Data;
import data.Instance;

public class DecisionTreeClassifier implements IClassifier {

	ArrayList<IHypothesis> hypotheses = new ArrayList<IHypothesis>();
	int maxDepth = -1;
	int numberOfHypotheses = -1;

	@SuppressWarnings("rawtypes")
	private HashMap decisionTree;

	public DecisionTreeClassifier(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	@Override
	public void setNoOfHypotheses(int no) {
		this.numberOfHypotheses = no;
	}

	@Override
	public int getNoOfHypotheses() {
		return numberOfHypotheses;
	}

	@Override
	public void generateHypothese(Data data) {
		ArrayList<Integer> features = new ArrayList<Integer>();
		for (int i = 0; i < data.getNumberOfFeatures(); i++) {
			features.add(i);
		}
		ArrayList<Instance> instances = data.getListOfTrainInstances();
		decisionTree = quinlanDecisionTree(instances, features, 0);
		DecisionTreeHypothesis hypothesis = new DecisionTreeHypothesis();
		hypothesis.setDecisionTree(decisionTree);
		hypotheses.add(hypothesis);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HashMap quinlanDecisionTree(ArrayList<Instance> instances, ArrayList<Integer> features, int depth) {
		if (instances == null || instances.size() == 0 || features.size() == 0 || depth == maxDepth) {
			return getLeaf(instances, features);
		}
		// <feature, <value, List<Instance>>>
		HashMap<Integer, HashMap<Integer, ArrayList<Instance>>> instancesPerValue = new HashMap<Integer, HashMap<Integer, ArrayList<Instance>>>();
		// <feature, <value, entropy>>
		HashMap<Integer, HashMap<Integer, Double>> entropies = new HashMap<Integer, HashMap<Integer, Double>>();

		for (Integer feature : features) {
			instancesPerValue.put(feature, divideInstancesPerValue(feature, instances));
			entropies.put(feature, computeEntropiesPerValue(feature, instancesPerValue));
		}

		int bestFeature = computeLowestEntropy(entropies, instancesPerValue, instances);
		if (bestFeature == -1) {
			for (Integer feature : features) {
				computeEntropiesPerValue(feature, instancesPerValue);
			}
		}

		HashMap<Integer, ArrayList<Instance>> valuesOfBestFeature = instancesPerValue.get(bestFeature);
		Set<Integer> valuesSet = valuesOfBestFeature.keySet();
		HashMap<Integer, HashMap> node = new HashMap<Integer, HashMap>();
		node.put(bestFeature, new HashMap<Integer, HashMap>());
		for (Integer value : valuesSet) {
			if (Math.abs(entropies.get(bestFeature).get(value)) < 0.000000001) {
				node.get(bestFeature).put(value, instancesPerValue.get(bestFeature).get(value).get(0).getLabel());
			} else {
				ArrayList<Integer> copyOfFeatures = (ArrayList<Integer>) features.clone();
				copyOfFeatures.remove(copyOfFeatures.indexOf(bestFeature));
				node.get(bestFeature).put(value, quinlanDecisionTree(instancesPerValue.get(bestFeature).get(value), copyOfFeatures, depth + 1));
			}
		}
		return node;
	}

	private int computeLowestEntropy(HashMap<Integer, HashMap<Integer, Double>> entropies,
			HashMap<Integer, HashMap<Integer, ArrayList<Instance>>> instancesPerValue, ArrayList<Instance> instances) {

		Set<Integer> featureSet = entropies.keySet();
		// if there are two or more features which have the minimal entropy, the first one is chosen
		double minEntropy = Double.POSITIVE_INFINITY;
		int bestFeature = -1;
		double sumWeightsFeature = 0;
		for (Instance instance : instances) {
			sumWeightsFeature += instance.getWeight();
		}
		for (Integer feature : featureSet) {
			double entropyPerFeature = 0;
			Set<Integer> values = entropies.get(feature).keySet();
			for (Integer value : values) {
				double sumWeightsValue = 0;
				for (Instance instance : instancesPerValue.get(feature).get(value)) {
					sumWeightsValue += instance.getWeight();
				}
				entropyPerFeature += (sumWeightsValue / sumWeightsFeature) * entropies.get(feature).get(value);
			}
			if (entropyPerFeature < minEntropy) {
				minEntropy = entropyPerFeature;
				bestFeature = feature;
			}
		}
		return bestFeature;
	}

	private HashMap<Integer, Double> computeEntropiesPerValue(int feature, HashMap<Integer, HashMap<Integer, ArrayList<Instance>>> instancesPerValue) {
		HashMap<Integer, Double> entropiesPerValue = new HashMap<Integer, Double>();
		Set<Integer> values = instancesPerValue.get(feature).keySet();
		for (Integer value : values) {
			HashMap<Integer, Double> probabilitiesPerLabel = new HashMap<Integer, Double>();
			instancesPerValue.get(feature).get(value);
			double probabilityPerValue = 0;
			for (Instance instance : instancesPerValue.get(feature).get(value)) {
				probabilityPerValue += instance.getWeight();
			}
			for (Instance instance : instancesPerValue.get(feature).get(value)) {
				if (!probabilitiesPerLabel.containsKey(instance.getLabel())) {
					probabilitiesPerLabel.put(instance.getLabel(), instance.getWeight());
				} else {
					probabilitiesPerLabel.put(instance.getLabel(), probabilitiesPerLabel.get(instance.getLabel()) + instance.getWeight());
				}
			}
			Set<Integer> labels = probabilitiesPerLabel.keySet();
			double entropy = 0;
			for (Integer label : labels) {
				double probability = probabilitiesPerLabel.get(label) / probabilityPerValue;
				if (probability == 0 || ((Double) probability).isNaN()) {
					// probability = Double.MIN_VALUE; (same behavior as continue)
					continue;
				}
				entropy += probability * (Math.log(probability) / Math.log(2));
			}
			entropy *= -1;
			// <value, entropy>
			entropiesPerValue.put(value, entropy);
		}
		return entropiesPerValue;
	}

	private HashMap<Integer, ArrayList<Instance>> divideInstancesPerValue(Integer feature, ArrayList<Instance> instances) {
		HashMap<Integer, ArrayList<Instance>> instancesPerValue = new HashMap<Integer, ArrayList<Instance>>();
		for (Instance instance : instances) {
			if (!instancesPerValue.containsKey(instance.getFeatures().get(feature))) {
				ArrayList<Instance> instancesForIthFeature = new ArrayList<Instance>();
				instancesForIthFeature.add(instance);
				instancesPerValue.put(instance.getFeatures().get(feature), instancesForIthFeature);
			} else {
				instancesPerValue.get(instance.getFeatures().get(feature)).add(instance);
			}
		}
		return instancesPerValue;
	}

	@SuppressWarnings("rawtypes")
	private HashMap getLeaf(ArrayList<Instance> instances, ArrayList<Integer> features) {

		if (instances == null || instances.size() == 0) {
			return null;
		}
		HashMap<Integer, Integer> labelOccurrences = new HashMap<Integer, Integer>();
		for (Instance instance : instances) {
			if (!labelOccurrences.containsKey(instance.getLabel())) {
				labelOccurrences.put(instance.getLabel(), 1);
			} else {
				labelOccurrences.put(instance.getLabel(), labelOccurrences.get(instance.getLabel()) + 1);
			}
		}

		Collection<Integer> occurrences = labelOccurrences.values();
		double maxOccurrence = Collections.max(occurrences);
		int winner = -1;
		Set<Integer> labels = labelOccurrences.keySet();
		// in case of ambiguous trainings sets just one of the labels with max votes is chosen (still better than pretend that its unknown)
		for (Integer label : labels) {
			if (maxOccurrence == labelOccurrences.get(label)) {
				winner = label;
				break;
			}
		}
		HashMap<Integer, HashMap> leaf = new HashMap<Integer, HashMap>();
		leaf.put(winner, null);
		return leaf;
	}

	@Override
	public ArrayList<IHypothesis> getHypotheses() {
		return hypotheses;
	}

	@Override
	public String toString() {
		return "DTC-" + maxDepth + " | Number of hypotheses: " + numberOfHypotheses;
	}
}
