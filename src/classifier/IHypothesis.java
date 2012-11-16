package classifier;

import data.Instance;

public interface IHypothesis {

	public int evaluate(Instance instance);

	public double getWeight();
	
	public void setWeight(double weight);

}
