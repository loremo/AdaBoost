package data.preprocessing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;

public class DataReader {
	
	private String filepath;
	private int numberOfFeatures = -1;
	private boolean invalidFeatures = false;
	private LinkedList<Integer> missingAttributes = new LinkedList<Integer>();
	
	public DataReader(String filepath) {
		this.filepath = filepath;
	}
	
	public ArrayList<ArrayList<Float>> read() throws IOException {
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

	public int getNumberOfFeatures() {
		return numberOfFeatures;
	}

	public boolean hasInvalidFeatures() {
		return invalidFeatures;
	}

	public LinkedList<Integer> getMissingAttributes() {
		return missingAttributes;
	}

}
