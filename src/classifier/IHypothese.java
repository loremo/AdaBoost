package classifier;

import java.util.Map;

import data.Feature;

public interface IHypothese {

	public Map<Integer, Integer> evaluate(Feature feature);

	public double getWeight();

}
