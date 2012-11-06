package classifier;

import java.util.ArrayList;

public interface IClassifier {
	
	public void setNoOfHypotheses(int no);
	
	public int getNoOfHypotheses();
	
	public void generateHypothese();
	
	public ArrayList<IHypothese> getHypotheses();
}
