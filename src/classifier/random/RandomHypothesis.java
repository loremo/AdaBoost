package classifier.random;

import java.util.Random;
import java.util.Set;

import classifier.IHypothesis;
import data.Instance;

public class RandomHypothesis implements IHypothesis {

	private double weight = 0;
	Set<Integer> labels;
	
	@Override
	public int evaluate(Instance instance) {
		Random generator = new Random();
//		return labels.toArray().get(generator.nextInt(labels.size()));
		return 0;
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

}
