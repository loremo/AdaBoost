package data.preprocessing;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import data.Instance;

public class DataDiscretizerTest {

	ArrayList<ArrayList<Float>> data = null;

	@Before
	public void setUp() {
		data = new ArrayList<ArrayList<Float>>();
		for (int i = 0; i < 10; i++) {
			ArrayList<Float> list = new ArrayList<Float>();
			list.add((float) i);
			list.add(0f); // label
			data.add(list);
		}
	}
	
	@Test
	public void testDiscretizeAttributes_average() {
		DataDiscretizer discretizer = new DataDiscretizer(data);
		ArrayList<Instance> instances = discretizer.discretizeAttributes();
		System.out.println(instances);
		assertTrue(false);
	}

}
