package classifier;

import java.util.ArrayList;

import data.Data;

public interface IClassifier {

	public void setNoOfHypotheses(int no);

	public int getNoOfHypotheses();

	public void generateHypothese(Data data);

	public ArrayList<IHypothesis> getHypotheses();

	public String toString();

	String toStringShort();
}
