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
			controller.processString("add primary arg");
			assertEquals("Added primary arg\n", outContent.toString());
			outContent.reset();
			
			//primary argument + time using --time
			controller.processString("add primary arg and time 1 --time 29/10/2014");
			assertEquals("Added primary arg and time 1\n", outContent.toString());
			outContent.reset();
			
			//primary argument + time using -t
			controller.processString("add primary arg and time 2 -t 29/10/2014");
			assertEquals("Added primary arg and time 2\n", outContent.toString());
			outContent.reset();
			
			//primary argument + deadline using --deadline
			controller.processString("add primary arg and deadline 1 --deadline 01/11/2014");
			assertEquals("Added primary arg and deadline 1\n", outContent.toString());
			outContent.reset();
			
			//primary argument + deadline using -d
			controller.processString("add primary arg and deadline 2 -d 01/11/2014");
			assertEquals("Added primary arg and deadline 2\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using --description
			controller.processString("add primary arg and desc 1 --description empty description");
			assertEquals("Added primary arg and desc 1\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using -desc
			controller.processString("add primary arg and desc 2 -desc empty description");
			assertEquals("Added primary arg and desc 2\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using --information
			controller.processString("add primary arg and desc 3 --information empty description");
			assertEquals("Added primary arg and desc 3\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using -info
			controller.processString("add primary arg and desc 4 -info empty description");
			assertEquals("Added primary arg and desc 4\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using -a
			controller.processString("add primary arg and desc 5 -a empty description");
			assertEquals("Added primary arg and desc 5\n", outContent.toString());
			outContent.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRetrive() {
		Controller controller = new Controller();
		try {
			//IMPT! delete all tasks
			
			//show all
			controller.processString("show");
			assertEquals("Task list is empty.\n", outContent.toString());
			outContent.reset();
			
			controller.processString("add task 1");
			controller.processString("add task 2");
			controller.processString("add task 3");
			controller.processString("add task 4");
			outContent.reset();
			controller.processString("show");
			String expectedFeedback = "Showing\n"
					+ "1. task 1\n"
					+ "2. task 2\n"
					+ "3. task 3\n"
					+ "4. task 4\n";
			assertEquals(expectedFeedback, outContent.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
	}
	
	@Test
	public void testDelete() {
		
	}
}
