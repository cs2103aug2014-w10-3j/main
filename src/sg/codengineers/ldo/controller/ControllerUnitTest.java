package sg.codengineers.ldo.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Parser;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.ParserImpl;

/**
 * This unit test aims to test whether the controller
 * correctly routes the data according to the command type.
 */
public class ControllerUnitTest {
	//This boolean is used to create a controller which uses stubs
	boolean UNIT_TEST_MODE = true;
	Controller controller = new Controller(UNIT_TEST_MODE);
	Parser parser = new ParserImpl();
	
	/**
	 * This function tests task creation.
	 */
	@Test
	public void testCreate() {
		try {
			Command command = parser.parse("add test1");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.CREATE, result.getCommandType());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This function tests task deletion.
	 */
	@Test
	public void testDelete() {
		try {
			Command command = parser.parse("delete 1");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.DELETE, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method tests task update.
	 */
	@Test
	public void testUpdate() {
		try {
			Command command = parser.parse("update 1");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.UPDATE, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSearch(){
		try {
			Command command = parser.parse("search a");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.SEARCH, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method tests task display/show.
	 */
	@Test
	public void testShow(){
		try {
			Command command = parser.parse("show");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.RETRIEVE, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUndo(){
		try {
			Command command = parser.parse("undo");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.UNDO, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHelp(){
		try {
			Command command = parser.parse("help");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.HELP, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
