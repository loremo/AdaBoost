package classifier;

import java.util.ArrayList;

public class DecisionTreeClassifier implements IClassifier {
	
	ArrayList<IHypothese> hypotheses = new ArrayList<IHypothese>();
	int depth = -1;
	int numberOfHypotheses = -1;
	
	public DecisionTreeClassifier(int depth) {
		this.depth = depth;
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
		return "DTC-" + depth + " | Number of hypotheses: " + numberOfHypotheses;
	}
}
