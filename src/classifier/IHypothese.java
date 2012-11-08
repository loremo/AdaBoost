package classifier;

import java.util.Map;

import data.Instance;

public interface IHypothese {

	public Map<Integer, Integer> evaluate(Instance feature);

	public double getWeight();

}
