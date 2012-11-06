package classifier;

import java.util.ArrayList;

public class NaiveBayesClassifier implements IClassifier {

	ArrayList<IHypothese> hypotheses = new ArrayList<IHypothese>();

	@Override
	public void setNoOfHypotheses(int no) {
		// TODO do it
	}

	@Override
	public int getNoOfHypotheses() {
		// TODO do it
		return 0;
	}

	@Override
	public void generateHypothese() {
		// TODO do it
		// TODO add hypothese to hypotheses
	}

	@Override
	public ArrayList<IHypothese> getHypotheses() {
		return hypotheses;
	}
}
