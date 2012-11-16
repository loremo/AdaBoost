package data.preprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import core.GlobalSettings;
import data.Data;
import data.Instance;

public class Preprocessor {

	GlobalSettings settings = null;
	

	public Preprocessor(GlobalSettings settings) {
		this.settings = settings;
	}

	//TODO: call from classifier
	public Data getData() throws IOException {
		DataReader reader = new DataReader(settings.getFilepath());
		ArrayList<ArrayList<Float>> rawData = reader.read();
		DataRepairer repairer = new DataRepairer(rawData);
		if (reader.hasInvalidFeatures()) {
			repairer.removeInvalidInstances(reader.getNumberOfFeatures());
		}
		if (!reader.getMissingAttributes().isEmpty()) {
			repairer.fillMissingFeatures(reader.getMissingAttributes());
		}
		DataDiscretizer decretizer = new DataDiscretizer(rawData);
		ArrayList<Instance> instances = decretizer.discretizeAttributes();
		
		Data data = subdivideInstances(instances, settings.getPercentage());
		data.setNumberOfFeatures(reader.getNumberOfFeatures() - 1);
		return data;
	}

	public Data subdivideInstances(ArrayList<Instance> instances, int percentage) {
		Data data = new Data();
//		Collections.shuffle(instances); //TODO: uncomment again?
		int threshold = (int) (instances.size() * (double) percentage / 100.0);
		List<Instance> trainInstances = instances.subList(0, threshold);
		double weight = 1.0 / (double) trainInstances.size(); 
		HashMap<Integer, List<Instance>> trainData = new HashMap<Integer, List<Instance>>();
		for (int i = 0; i < trainInstances.size(); i++) {
			trainInstances.get(i).setWeight(weight);
			if (!trainData.containsKey(trainInstances.get(i).getLabel())) {
				ArrayList<Instance> insancesWithSameLabel = new ArrayList<Instance>();
				insancesWithSameLabel.add(trainInstances.get(i));
				trainData.put(trainInstances.get(i).getLabel(), insancesWithSameLabel);
			} else {
				trainData.get(trainInstances.get(i).getLabel()).add(trainInstances.get(i));
			}
		}
		data.setTrainData(trainData);
		data.setTestData(instances.subList(threshold, instances.size()));
		return data;
	}

	public static void main(String[] args) {

		ArrayList<ArrayList<Float>> f = new ArrayList<ArrayList<Float>>();

		ArrayList<Float> a = new ArrayList<Float>();
		a.add(1f);
		a.add(2f);
		a.add(3f);
		ArrayList<Float> b = new ArrayList<Float>();
		b.add(1f);
		b.add(2f);
		b.add(3f);

		f.add(a);
		f.add(b);
		for (ArrayList<Float> list : f) {
			list.set(0, 42f);
		}
		System.out.println(f);
	}
}
