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
		
		//only primary argument
		controller.processString("add dummy task");
		assertEquals("Added create task #1\n", outContent.toString());
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
