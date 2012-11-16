package core;

import java.util.ArrayList;
import java.util.Set;

public class TrainingResult {

	GlobalSettings settings = new GlobalSettings();
	Set<Integer> originalLabels = null;
	ArrayList<Double> averages = new ArrayList<Double>();
	ArrayList<Double> deviations = new ArrayList<Double>();

	public TrainingResult(GlobalSettings settings) {
		this.settings = settings;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < settings.getClassifiers().size(); i++) {
			s += settings.getClassifiers().get(i) + ":\n";
			s += "\tAverage           : " + averages.get(i) + "\n";
			s += "\tStandard deviation: " + deviations.get(i);
			if(i < settings.getClassifiers().size() - 1) {
				s += "\n";
			}
		}
		return s;
	}

	public ArrayList<Double> getAverages() {
		return averages;
	}

	public void setAverages(ArrayList<Double> averages) {
		this.averages = averages;
	}

	public ArrayList<Double> getDeviations() {
		return deviations;
	}

	public void setDeviations(ArrayList<Double> deviations) {
		this.deviations = deviations;
	}

}
