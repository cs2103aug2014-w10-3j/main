package sg.codengineers.ldo.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.CommandImpl;

public class ControllerUnitTest {
	boolean stub = true;
	Controller controller = new Controller(stub);
	
	@Test
	public void testCreate() {
		try {
			Command command = new CommandImpl("add test1");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.CREATE, result.getCommandType());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDelete() {
		try {
			Command command = new CommandImpl("delete test1");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.DELETE, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdate() {
		try {
			Command command = new CommandImpl("update test1");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.UPDATE, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRetrieve(){
		try {
			Command command = new CommandImpl("show test1");
			Result result = controller.executeCommand(command);
			assertEquals(CommandType.RETRIEVE, result.getCommandType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
