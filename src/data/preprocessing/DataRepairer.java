package data.preprocessing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataRepairer {
	
	ArrayList<ArrayList<Float>> data = null;

	public DataRepairer(ArrayList<ArrayList<Float>> rawData) {
		this.data = rawData;
	}

	public void removeInvalidInstances(int numberOfFeatures) {
		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).size() != numberOfFeatures) {
				data.remove(i--);
			}
		}
	}

	public void fillMissingFeatures(List<Integer> missingAttributes) {
		for (Integer missingAttribute : missingAttributes) {
			float sum = 0;
			int count = 0;
			//TODO: only compute average for the same label
			for (int i = 0; i < data.size(); i++) {
				if (!Float.isNaN(data.get(i).get(missingAttribute))) {
					sum += data.get(i).get(missingAttribute);
					count++;
				}
			}
			float average = sum / count;
			for (int i = 0; i < data.size(); i++) {
				if (Float.isNaN(data.get(i).get(missingAttribute))) {
					data.get(i).set(missingAttribute, average);
				}
			}
		}

	}

}
