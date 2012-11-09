package data.preprocessing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import core.GlobalSettings;

import data.ClassIntervals;
import data.Data;
import data.Instance;

public class Preprocessor {

	GlobalSettings settings = null;
	
	public Preprocessor(GlobalSettings settings) {
		this.settings = settings;
	}

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
		
		return subdivideInstances(instances, settings.getPercentage());
	}

	private Data subdivideInstances(ArrayList<Instance> instances, int percentage) {
		Data data = new Data();
		Collections.shuffle(instances);
		int threshold = (int) (instances.size() * (double) percentage / 100.0);
		List<Instance> trainData = instances.subList(0, threshold);
		double weight = 1.0 / (double) trainData.size(); 
		for (int i = 0; i < trainData.size(); i++) {
			trainData.get(i).setWeight(weight);
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
