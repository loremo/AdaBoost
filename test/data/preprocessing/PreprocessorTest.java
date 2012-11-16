package data.preprocessing;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.Test;

import core.GlobalSettings;
import data.Data;
import data.Instance;

public class PreprocessorTest {

	@Test
	public void testPreprocessData_readData() throws IOException {

		String filename;
		GlobalSettings settings = new GlobalSettings();
		Preprocessor preprocessor = null;
		
		File file = File.createTempFile("test", "1");
		filename = file.getAbsolutePath();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(filename)));

		writer.write("11,12,13,14,15,1");
		writer.newLine();
		writer.write("21,	22,	23,	24,	25,	2");
		writer.newLine();
		writer.write("31 32 33 34 35 1");
		writer.newLine();
		writer.write("41	42	43	44	45	2");
		writer.newLine();
		writer.write("51	52		54	55	1"); // invalid
		writer.newLine();
		writer.write("61	62	64	65	2"); // invalid
		writer.newLine();
		writer.write("71	72	?	74	75	1");
		writer.newLine();
		writer.write("81,,83,84,85,2");
		writer.newLine();
		writer.close();

		settings.setFilepath(filename);
		preprocessor = new Preprocessor(settings);
		Data data = preprocessor.getData();
		assertEquals(6, data.getTestData().size() + data.getTrainData().size());
	}

	@Test
	public void testPreprocessData_discretize() throws IOException {

		String filename;
		GlobalSettings settings = new GlobalSettings();
		Preprocessor preprocessor = null;
		
		File file = File.createTempFile("test", "1");
		filename = file.getAbsolutePath();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(filename)));

		writer.write("1, 1");
		writer.newLine();
		writer.write("1, 1");
		writer.newLine();
		writer.write("1, 1");
		writer.newLine();
		writer.write("?, 1");
		writer.newLine();
		writer.write("5, 2");
		writer.newLine();
		writer.write("1, 1");
		writer.newLine();
		writer.close();

		settings.setFilepath(filename);
		settings.setPercentage(100);
		preprocessor = new Preprocessor(settings);
		Data data = preprocessor.getData();
		int firstclass = 0;
		int secondclass = 0;
//		for (Instance instance : data.getTrainData()) {
//			if (instance.getFeatures().get(0) == 0) {
//				firstclass++;
//			} else if (instance.getFeatures().get(0) == 1) {
//				secondclass++;
//			}
//		}
//		assertEquals(firstclass, 4);		
//		assertEquals(secondclass, 2);
		
	}
}
