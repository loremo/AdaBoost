package data;

import java.util.ArrayList;

public class ClassIntervals {
	private ArrayList<Float> intervals = new ArrayList<Float>();

	public ArrayList<Float> getIntervals() {
		return intervals;
	}
	
	public void addIntervallThreshold(float threshold) {
		intervals.add(threshold);
	}

}
