package data.preprocessing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DataRepairerTest {

	ArrayList<ArrayList<Float>> data = null;

	@Before
	public void setUp() {
		data = new ArrayList<ArrayList<Float>>();
		for (int i = 0; i < 3; i++) {
			ArrayList<Float> list = new ArrayList<Float>();
			list.add(1f + i * 10);
			list.add(2f + i * 10);
			list.add(Float.NaN);
			list.add(4f + i * 10);
			data.add(list);
		}
	}

	@Test
	public void testRemoveInvalidInstances_average() {
		DataRepairer repairer = new DataRepairer(data);
		repairer.removeInvalidInstances(4);
		assertEquals(3, data.size());
		data.get(0).add(42f);
		repairer.removeInvalidInstances(4);
		assertEquals(2, data.size());
		data.get(0).remove(0);
		repairer.removeInvalidInstances(4);
		assertEquals(1, data.size());

	}

	@Test
	public void fillMissingFeatures_NaNColumn() {
		DataRepairer repairer = new DataRepairer(data);
		List<Integer> missingAttributes = new LinkedList<Integer>();
		missingAttributes.add(2);
		repairer.fillMissingFeatures(missingAttributes);

		for (int i = 0; i < 3; i++) {
			assertEquals(0f, data.get(i).get(2), 0.000001);
		}

	}

	@Test
	public void fillMissingFeatures_singleNotNaN() {
		data.get(0).set(2, 42f);
		DataRepairer repairer = new DataRepairer(data);
		List<Integer> missingAttributes = new LinkedList<Integer>();
		missingAttributes.add(2);
		repairer.fillMissingFeatures(missingAttributes);

		for (int i = 0; i < 3; i++) {
			assertEquals(42f, data.get(i).get(2), 0.000001);
		}
	}

	@Test
	public void fillMissingFeatures_average() {
		data.get(0).set(2, 42f);
		data.get(1).set(2, 0f); // 0,42 : average = 21
		DataRepairer repairer = new DataRepairer(data);
		List<Integer> missingAttributes = new LinkedList<Integer>();
		missingAttributes.add(2);
		repairer.fillMissingFeatures(missingAttributes);

		assertEquals(21f, data.get(2).get(2), 0.000001);

	}

}
