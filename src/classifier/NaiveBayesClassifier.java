package classifier;

import java.util.ArrayList;

public class NaiveBayesClassifier implements IClassifier {

	ArrayList<IHypothese> hypotheses = new ArrayList<IHypothese>();
	int numberOfHypotheses = -1;
	
	@Override
	public void setNoOfHypotheses(int no) {
		this.numberOfHypotheses = no;
	}

	@Override
	public int getNoOfHypotheses() {
		return numberOfHypotheses;
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
	
	@Override
	public String toString() {
		return "NBC" + " | Number of hypotheses: " + numberOfHypotheses;
	}
}
