package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import classifier.IClassifier;
import classifier.IHypothesis;
import data.Instance;

public class Test {

	GlobalSettings settings = new GlobalSettings();

	public Test(GlobalSettings settings) {
		this.settings = settings;
	}

	public double testError(List<Instance> testData) {
		int hits = 0;
		int misses = 0;
		for (Instance instance : testData) {
			// <label, weighted votes>
			HashMap<Integer, Double> votes = new HashMap<Integer, Double>();
			for (IClassifier classifier : settings.getClassifiers()) {
				for (IHypothesis hypotheses : classifier.getHypotheses()) {
					int vote = hypotheses.evaluate(instance);
					double weight = hypotheses.getWeight();
					if (!votes.containsKey(vote)) {
						votes.put(vote, weight);
					} else {
						votes.put(vote, weight + votes.get(vote));
					}
				}
			}

			Collection<Double> weightedVotes = votes.values();
			double maxVote = Collections.max(weightedVotes);
			int winner = -1;
			Set<Integer> labels = votes.keySet();
			for (Integer label : labels) {
				if (maxVote == votes.get(label)) {
					winner = label;
					break;
				}
			}
			if (winner == instance.getLabel()) {
				hits++;
			} else {
				misses++;
			}
		}
		return (double) hits / (double) (hits + misses);
	}

	public TrainingResult trainError(HashMap<Integer, List<Instance>> trainData) {
		Set<Integer> labels = trainData.keySet();
		ArrayList<Double> averages = new ArrayList<Double>();
		ArrayList<Double> deviations = new ArrayList<Double>();
		for (IClassifier classifier : settings.getClassifiers()) {
			ArrayList<Double> accuracies = new ArrayList<Double>(classifier.getHypotheses().size());
			int count = 0; // TODO: delete
			for (IHypothesis hypothesis : classifier.getHypotheses()) {
				count++;
				int hits = 0;
				int misses = 0;
				for (Integer label : labels) {
					List<Instance> instancesWithSameLabel = trainData.get(label);
					for (Instance instance : instancesWithSameLabel) {
						if (hypothesis.evaluate(instance) == instance.getLabel()) {
							hits++;
						} else {
							misses++;
						}
					}
				}
				double accuracy = (double) hits / (double) (hits + misses);
				if (count == 19) {
					System.out.println("test");
				}
				accuracies.add(accuracy);
			}
			for (Double accuracy : accuracies) {
				System.out.println("train accuracy: " + accuracy);
			}
			double accuraciesSum = 0;
			for (Double accuracy : accuracies) {
				accuraciesSum += accuracy;
			}
			double average = accuraciesSum / accuracies.size();
			double variance = 0;
			for (Double accuracy : accuracies) {
				variance += (accuracy - average) * (accuracy - average);
			}
			double deviation = Math.sqrt(variance);
			averages.add(settings.getClassifiers().indexOf(classifier), average);
			deviations.add(settings.getClassifiers().indexOf(classifier), deviation);
		}
		TrainingResult result = new TrainingResult(settings);
		result.setAverages(averages);
		result.setDeviations(deviations);
		return result;
	}

}
