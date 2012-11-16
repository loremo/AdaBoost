package classifier.DTC;

import java.util.HashMap;
import java.util.Set;

import classifier.IHypothesis;
import data.Instance;

public class DecisionTreeHypothesis implements IHypothesis {

	private double weight = 0;
	@SuppressWarnings("rawtypes")
	private HashMap decisionTree = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int evaluate(Instance instance) {

		Set<Integer> features;
		HashMap subTree = decisionTree;
		Set<Integer> values;

		while ((features = subTree.keySet()) != null) {
			if (features.size() != 1) { // TODO: delete
				System.out.println("ERROR");
			}
			int feature = (Integer) features.toArray()[0];
			try {
				values = ((HashMap) subTree.get(feature)).keySet();
			} catch (Exception e) {
				return feature;
			}

			subTree = (HashMap) subTree.get(feature);

			boolean matched = false;
			for (Integer value : values) {
				if (instance.getFeatures().get(feature) == value) {
					matched = true;
					try {
						subTree = (HashMap) subTree.get(value);
						if (subTree == null) {
							return Integer.MIN_VALUE;
						}
					} catch (Exception e) {
						return (Integer) subTree.get(value);
					}
					break;
				}			
			}
			if (!matched) {
				return Integer.MIN_VALUE;				
			}	
		}
		return Integer.MIN_VALUE;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(double weight) {
		this.weight = weight;
	}

	@SuppressWarnings("rawtypes")
	public void setDecisionTree(HashMap decisionTree) {
		this.decisionTree = decisionTree;
	}
}
