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
			controller.processCommand("add primary arg");
			assertEquals("Added primary arg\n", outContent.toString());
			outContent.reset();
			
			//primary argument + time using --time
			controller.processCommand("add primary arg and time 1 --time 29/10/2014");
			assertEquals("Added primary arg and time 1\n", outContent.toString());
			outContent.reset();
			
			//primary argument + time using -t
			controller.processCommand("add primary arg and time 2 -t 29/10/2014");
			assertEquals("Added primary arg and time 2\n", outContent.toString());
			outContent.reset();
			
			//primary argument + deadline using --deadline
			controller.processCommand("add primary arg and deadline 1 --deadline 01/11/2014");
			assertEquals("Added primary arg and deadline 1\n", outContent.toString());
			outContent.reset();
			
			//primary argument + deadline using -d
			controller.processCommand("add primary arg and deadline 2 -d 01/11/2014");
			assertEquals("Added primary arg and deadline 2\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using --description
			controller.processCommand("add primary arg and desc 1 --description empty description");
			assertEquals("Added primary arg and desc 1\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using -desc
			controller.processCommand("add primary arg and desc 2 -desc empty description");
			assertEquals("Added primary arg and desc 2\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using --information
			controller.processCommand("add primary arg and desc 3 --information empty description");
			assertEquals("Added primary arg and desc 3\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using -info
			controller.processCommand("add primary arg and desc 4 -info empty description");
			assertEquals("Added primary arg and desc 4\n", outContent.toString());
			outContent.reset();
			
			//primary argument + description using -a
			controller.processCommand("add primary arg and desc 5 -a empty description");
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
			controller.processCommand("show");
			assertEquals("Task list is empty.\n", outContent.toString());
			outContent.reset();
			
			controller.processCommand("add task 1");
			controller.processCommand("add task 2");
			controller.processCommand("add task 3");
			controller.processCommand("add task 4");
			outContent.reset();
			
			//show (all)
			controller.processCommand("show");
			String expectedFeedback = "Showing\n"
					+ "1. task 1\n"
					+ "2. task 2\n"
					+ "3. task 3\n"
					+ "4. task 4\n";
			assertEquals(expectedFeedback, outContent.toString());
			outContent.reset();
			
			//show all
			controller.processCommand("show all");
			assertEquals(expectedFeedback, outContent.toString());
			outContent.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		Controller controller = new Controller();
		try{
			//delete all tasks
			
			controller.processCommand("add task 1");
			controller.processCommand("add task 2");
			controller.processCommand("add task 3");
			controller.processCommand("add task 4");
			outContent.reset();
			
			//update before show, will be from today's list shown at the beginning of the program
			controller.processCommand("edit 1 -n task one");
			assertEquals("Invalid index. Please view the latest list first.", outContent.toString());
			outContent.reset();
			
			//update name
			controller.processCommand("show");
			outContent.reset();
			controller.processCommand("update 1 -n task one");
			assertEquals("Updated task one", outContent.toString());
			outContent.reset();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete() {
		Controller controller = new Controller();
		try{
			//delete all tasks
			
			controller.processCommand("add task 1");
			controller.processCommand("add task 2");
			controller.processCommand("add task 3");
			controller.processCommand("add task 4");
			outContent.reset();
			
			//delete before show, will be from today's list shown at the beginning of the program
			controller.processCommand("delete 1");
			assertEquals("Invalid index. Please view the latest list first.", outContent.toString());
			outContent.reset();
			
			//[corner case] delete first index
			controller.processCommand("show");
			outContent.reset();
			controller.processCommand("delete 1");
			assertEquals("Deleted task 1", outContent.toString());
			outContent.reset();
			
			//[corner case] delete last index
			controller.processCommand("show");
			outContent.reset();
			controller.processCommand("delete 3");
			assertEquals("Deleted task 4", outContent.toString());
			outContent.reset();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
