package data;

import java.util.ArrayList;
import java.util.Map;

public class Result {
	ArrayList<Map<Integer, Integer>> results = new ArrayList<Map<Integer, Integer>>();

	public void add(Map<Integer, Integer> votes) {
		results.add(votes);
	}
}
