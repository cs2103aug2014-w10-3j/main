package sg.codengineers.ldo.test.system;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.codengineers.ldo.controller.Controller;

public class SystemTestCRUD {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void cleanUpStream() {
		System.setOut(null);
	}
	
	@Test
	public void testCreate() {
		Controller controller = new Controller();
		
		try{
			//only primary argument
			controller.processString("add primary argument");
			assertEquals("Added primary argument\n", outContent.toString());
			outContent.reset();
			
			//primary argument + time
			controller.processString("add primary argument and time -t 29/10/2014");
			assertEquals("Added primary argument and time\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description
			controller.processString("add primary argument and desc -desc empty description");
			assertEquals("Added primary argument and desc\n", outContent.toString());
			outContent.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRetrive() {
		
	}
	
	@Test
	public void testUpdate() {
		
	}
	
	@Test
	public void testDelete() {
		
	}
}
