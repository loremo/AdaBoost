package core;

import java.util.Random;

import classifier.IClassifier;
import classifier.IHypothesis;
import data.Data;
import data.Instance;

public class Training {

	GlobalSettings settings = new GlobalSettings();

	public Training(GlobalSettings settings) {
		this.settings = settings;
	}

	public void train(Data data) {
		for (IClassifier classifier : settings.getClassifiers()) {
			for (int i = 0; i < classifier.getNoOfHypotheses(); i++) {
				data.getListOfTrainInstances();
				classifier.generateHypothese(data);
				boolean errorSmallEnough = updateWeights(classifier.getHypotheses().get(classifier.getHypotheses().size() - 1), data);
				if (!errorSmallEnough) {
					// classifier.getHypotheses().remove(classifier.getHypotheses().size() - 1);
					classifier.getHypotheses().remove(i--);
				}
			}
		}
	}

	private boolean updateWeights(IHypothesis hypothesis, Data data) {
		double error = 0;
		double beta = 1.5;

		for (Instance instance : data.getListOfTrainInstances()) {
			if (hypothesis.evaluate(instance) != instance.getLabel()) {
				error += instance.getWeight();
			}
		}

		double numLabels = data.getTrainData().keySet().size();

		if (((Double) error).isNaN()) { // TODO: delete
			System.out.println("error is NaN");
		}

		if (error >= ((numLabels - 1.0) / numLabels)) {
			jiggleWeights(data, beta);
			return false;
		} else if (error > 0 && error < ((numLabels - 1.0) / numLabels)) {

			// double hypothesisWeight = Math.log((1.0 - error) / error) + Math.log(numLabels - 1);

			// double hypothesisWeight = Math.log((1.0 - error) / error);
			for (Instance instance : data.getListOfTrainInstances()) {
				if (hypothesis.evaluate(instance) != instance.getLabel()) {
					// double newWeight = instance.getWeight() * Math.exp(hypothesisWeight);
					double newWeight = instance.getWeight() * ((1 - error) / error) * (numLabels - 1);
					instance.setWeight(newWeight);
				}
			}

			normalizeWeights(data);

			// hypothesis.setWeight(hypothesisWeight);
			hypothesis.setWeight(Math.log(((1 - error) / error) * (numLabels - 1)));

		} else if (error == 0) {
			jiggleWeights(data, beta);

			hypothesis.setWeight(10.0 + Math.log(numLabels - 1));

		}

		System.out.println("error of hypothesis: " + error);
		return true;
	}

	private void jiggleWeights(Data data, double beta) {
		Random generator = new Random();
		int numInstances = data.getListOfTrainInstances().size();
		for (Instance instance : data.getListOfTrainInstances()) {
			int random = generator.nextInt((int) (2 * numInstances * beta));
			random -= (numInstances * beta);
			if (random == 0) {
				random = 1;
			}
			double jiggle = 1 / (double) random;
			instance.setWeight(Math.max(0.0, instance.getWeight() + jiggle));
		}
		normalizeWeights(data);
	}

	private void normalizeWeights(Data data) {
		double weightsSum = 0;
		for (Instance instance : data.getListOfTrainInstances()) {
			weightsSum += instance.getWeight();
			if (instance.getWeight() == Double.NaN) { // TODO: delete
				System.out.println("test");
			}
		}
		if (weightsSum == 0.0) { // TODO: delete
			System.out.println("test");
			
		}
		for (Instance instance : data.getListOfTrainInstances()) {
			instance.setWeight(instance.getWeight() / weightsSum);
		}
	}
}
