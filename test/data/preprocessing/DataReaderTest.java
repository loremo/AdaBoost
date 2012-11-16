package data.preprocessing;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.junit.Test;


public class DataReaderTest {
	
	@Test
	public void testRead_average() throws IOException {
	
	File file = File.createTempFile("test", "1");
	String filename = file.getAbsolutePath();
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

	DataReader reader = new DataReader(filename);
	ArrayList<ArrayList<Float>> data = reader.read();
	
	assertTrue(reader.hasInvalidFeatures());
	
	assertEquals(2, reader.getMissingAttributes().size());
	assertTrue(reader.getMissingAttributes().contains(1));
	assertTrue(reader.getMissingAttributes().contains(2));
	
	assertEquals(6, reader.getNumberOfFeatures());
	
	assertEquals(8, data.size());

	assertTrue(data.get(6).get(2).isNaN());
	assertTrue(data.get(7).get(1).isNaN());
	
}

}
