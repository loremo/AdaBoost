package core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import data.ClassIntervals;
import data.Data;
import data.Instance;

public class Preprocessor {

	GlobalSettings settings = null;

	private LinkedList<Integer> missingAttributes = new LinkedList<Integer>();
	private int numberOfFeatures = -1;
	private boolean invalidFeatures = false;

	public Preprocessor(GlobalSettings settings) {
		this.settings = settings;
	}

	public Data preprocessData() throws IOException {
		ArrayList<ArrayList<Float>> rawData = readData(settings.getFilepath());
		if (invalidFeatures) {
			removeInvalidInstances(rawData);
		}
		if (!missingAttributes.isEmpty()) {
			fillMissingFeatures(rawData);
		}
		numberOfFeatures--; // label is not a feature;
		ArrayList<ClassIntervals> intervals = computeIntervalls(rawData);
		ArrayList<Instance> instances = discretizeAttributes(rawData, intervals);
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

	private ArrayList<Instance> discretizeAttributes(ArrayList<ArrayList<Float>> instances, ArrayList<ClassIntervals> intervals) {
		ArrayList<Instance> discretizedAttributes = new ArrayList<Instance>();
		int numOfIntervals = intervals.get(0).getIntervals().size();
		for (ArrayList<Float> instance : instances) {
			Instance features = new Instance();
			for (int i = 0; i < numberOfFeatures; i++) {
				for(int j = 0; j < numOfIntervals; j++) {
					if (instance.get(i) <= intervals.get(i).getIntervals().get(j)) {
						features.addFeature(j);
						break;
					} else if (j == numOfIntervals - 1) {
						features.addFeature(numOfIntervals);						
					}
				}
			}
			features.setLabel(Math.round((instance.get(numberOfFeatures))));
			discretizedAttributes.add(features);
		}
		return discretizedAttributes;

	}

	private ArrayList<ClassIntervals> computeIntervalls(ArrayList<ArrayList<Float>> instances) {
		int numberOfClasses = Math.max((int) Math.pow(instances.size(), 1.0 / 3.0), 2); // N^(1/3)
		int sampleSize = (int) (Math.log(instances.size()) * 42);
		if (sampleSize > instances.size()) {
			sampleSize = instances.size();
		}
		ArrayList<ArrayList<Float>> samples = new ArrayList<ArrayList<Float>>();
		for (int i = 0; i < numberOfFeatures; i++) {
			samples.add(new ArrayList<Float>(sampleSize));
		}
		ArrayList<Integer> randomNumbers = randomWithoutDuplicates(sampleSize, instances.size());
		for (int i = 0; i < randomNumbers.size(); i++) { // TODO: sampleSize > instances.size() else zweig?
			for (int j = 0; j < numberOfFeatures; j++) {
				samples.get(j).add(instances.get(randomNumbers.get(i)).get(j));
			}
		}
		for (int i = 0; i < numberOfFeatures; i++) {
			Collections.sort(samples.get(i));
		}
		ArrayList<ClassIntervals> intervals = new ArrayList<ClassIntervals>();
		double intervalSize = (double) sampleSize / (double) numberOfClasses;
		for (int i = 0; i < numberOfFeatures; i++) {
			ClassIntervals intervallForOneAttribute = new ClassIntervals();
			for (int j = 1; j < numberOfClasses; j++) {
				intervallForOneAttribute.addIntervallThreshold(samples.get(i).get((int) (j * intervalSize)));
			}
			intervals.add(intervallForOneAttribute);
		}
		return intervals;
	}

	private ArrayList<Integer> randomWithoutDuplicates(int size, int range) {
		ArrayList<Integer> generated = new ArrayList<Integer>();
		Random generator = new Random();
		int count = 0;
		while (count < size) {
			int index = generator.nextInt(range);
			if (!generated.contains(index)) {
				generated.add(index);
				count++;
			}
		}
		return generated;
	}

	private void removeInvalidInstances(ArrayList<ArrayList<Float>> features) {
		for (int i = 0; i < features.size(); i++) {
			if (features.get(i).size() != numberOfFeatures) {
				features.remove(i--);
			}
		}
	}

	private void fillMissingFeatures(ArrayList<ArrayList<Float>> features) {
		for (Integer missingAttribute : missingAttributes) {
			float sum = 0;
			int count = 0;
			//TODO: only compute average for the same label
			for (int i = 0; i < features.size(); i++) {
				if (!Float.isNaN(features.get(i).get(missingAttribute))) {
					sum += features.get(i).get(missingAttribute);
					count++;
				}
			}
			float average = sum / count;
			for (int i = 0; i < features.size(); i++) {
				if (Float.isNaN(features.get(i).get(missingAttribute))) {
					features.get(i).set(missingAttribute, average);
				}
			}
		}

	}

	private ArrayList<ArrayList<Float>> readData(String filepath) throws IOException {
		ArrayList<ArrayList<Float>> features = new ArrayList<ArrayList<Float>>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.trim().split("(,)(\\t)+|(\\t)+|(,)( )+|( )+|(,)");
				if (numberOfFeatures != values.length && numberOfFeatures != -1) {
					invalidFeatures = true;
				}
				numberOfFeatures = Math.max(numberOfFeatures, values.length);
				ArrayList<Float> feature = new ArrayList<Float>();
				for (int i = 0; i < values.length; i++) {
					try {
						feature.add(Float.parseFloat(values[i]));
					} catch (Exception e) {
						if (!missingAttributes.contains(i)) {
							missingAttributes.add(i);
						}
						feature.add(Float.NaN);
					}
				}
				features.add(feature);
			}
		} finally {
			reader.close();
		}
		return features;
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
