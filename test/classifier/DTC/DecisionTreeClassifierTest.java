package classifier.DTC;

import java.util.ArrayList;

import org.junit.Test;

import core.GlobalSettings;
import core.Training;

import data.Data;
import data.Instance;
import data.preprocessing.Preprocessor;


public class DecisionTreeClassifierTest {

	@Test
	public void testGenerateHypothesis(){
		ArrayList<Instance> instances = new ArrayList<Instance>();
		Instance i = new Instance();
		i.addFeature(1);
		i.addFeature(0);
		i.addFeature(0);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(0);
		i.addFeature(0);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(0);
		i.addFeature(1);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(0);
		i.addFeature(0);
		i.addFeature(0);
		i.setLabel(0);
		instances.add(i);
		i = new Instance();
		i.addFeature(0);
		i.addFeature(0);
		i.addFeature(2);
		i.setLabel(0);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(1);
		i.addFeature(2);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(1);
		i.addFeature(2);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(1);
		i.addFeature(1);
		i.setLabel(0);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(1);
		i.addFeature(0);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(0);
		i.addFeature(1);
		i.addFeature(2);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(0);
		i.addFeature(1);
		i.addFeature(1);
		i.setLabel(0);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(2);
		i.addFeature(0);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(2);
		i.addFeature(1);
		i.setLabel(1);
		instances.add(i);
		i = new Instance();
		i.addFeature(1);
		i.addFeature(2);
		i.addFeature(0);
		i.setLabel(0);
		instances.add(i);
		i = new Instance();
		i.addFeature(0);
		i.addFeature(2);
		i.addFeature(1);
		i.setLabel(0);
		instances.add(i);
		i = new Instance();
		i.addFeature(0);
		i.addFeature(2);
		i.addFeature(2);
		i.setLabel(1);
		instances.add(i);
		GlobalSettings settings = new GlobalSettings();
		settings.setPercentage(100);
		DecisionTreeClassifier dtc = new DecisionTreeClassifier(-1);
		dtc.setNoOfHypotheses(1);
		settings.addClassifier(dtc);
		
		Data data = new Preprocessor(settings).subdivideInstances(instances, 100);
		data.setNumberOfFeatures(3);
		
		Training training = new Training(settings);
		training.train(data);
		dtc.getHypotheses().get(0).evaluate(new Instance());
		
		
	}
}
