package data.preprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import data.ClassIntervals;
import data.Instance;

public class DataDiscretizer {

	private ArrayList<ArrayList<Float>> data = null;
	private int numberOfFeatures = -1;
	private ArrayList<Integer> numberOfDiscretizedClasses = new ArrayList<Integer>();

	public DataDiscretizer(ArrayList<ArrayList<Float>> rawData) {
		this.data = rawData;
		numberOfFeatures = rawData.get(0).size() - 1; // label isn't a feature
	}

	public ArrayList<Instance> discretizeAttributes() {
		ArrayList<Instance> discretizedAttributes = new ArrayList<Instance>();
		ArrayList<ClassIntervals> intervals = computeIntervalls();
		int numOfIntervals = intervals.get(0).getIntervals().size();
		for (ArrayList<Float> instance : data) {
			Instance features = new Instance();
			for (int i = 0; i < numberOfFeatures; i++) {
				for (int j = 0; j < numOfIntervals; j++) {
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

	public static int NUMBEROFINTERVALS = 5;
	
	private ArrayList<ClassIntervals> computeIntervalls() {
		// TODO: actual number of classes < computed number of classes
		// int numberOfIntervals = Math.max((int) Math.ceil(Math.pow(data.size(), 1.0 / 20.0)), 3); // N^(1/3)

//		int sampleSize = (int) (Math.log(data.size()) * 42); // TODO: uncomment again
		 int sampleSize = data.size();
		if (sampleSize > data.size()) {
			sampleSize = data.size();
		}
		ArrayList<ArrayList<Float>> samples = new ArrayList<ArrayList<Float>>();
		for (int i = 0; i < numberOfFeatures; i++) {
			samples.add(new ArrayList<Float>(sampleSize));
		}
		ArrayList<Integer> randomNumbers = randomWithoutDuplicates(sampleSize, data.size());
		for (int i = 0; i < randomNumbers.size(); i++) { // TODO: sampleSize > instances.size() else zweig?
			for (int j = 0; j < numberOfFeatures; j++) {
				samples.get(j).add(data.get(randomNumbers.get(i)).get(j));
			}
		}
		for (int i = 0; i < numberOfFeatures; i++) {
			Collections.sort(samples.get(i));
		}
		ArrayList<ClassIntervals> intervals = new ArrayList<ClassIntervals>();
		double intervalSize = (double) sampleSize / (double) NUMBEROFINTERVALS;
		for (int i = 0; i < numberOfFeatures; i++) {
			ClassIntervals intervallForOneAttribute = new ClassIntervals();
			for (int j = 1; j < NUMBEROFINTERVALS; j++) {
				intervallForOneAttribute.addIntervallThreshold(samples.get(i).get((int) (j * intervalSize)));
			}
			intervals.add(intervallForOneAttribute);
		}
		return intervals;
	}

	public ArrayList<Integer> getNumberOfDiscretizedClasses() {
		return numberOfDiscretizedClasses;
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

}
