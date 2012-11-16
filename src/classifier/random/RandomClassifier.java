package classifier.random;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import classifier.IClassifier;
import classifier.IHypothesis;
import data.Data;
import data.Instance;

public class RandomClassifier implements IClassifier {

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
		 RandomHypothesis hypothesis = new RandomHypothesis();
		 hypothesis.setLabels(data.getTrainData().keySet());
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
