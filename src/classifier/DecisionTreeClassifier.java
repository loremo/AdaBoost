package classifier;

import java.util.ArrayList;

public class DecisionTreeClassifier implements IClassifier {
	
	ArrayList<IHypothese> hypotheses = new ArrayList<IHypothese>();
	int depth = -1;
	
	public DecisionTreeClassifier(int depth) {
		this.depth = depth;
	}

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
