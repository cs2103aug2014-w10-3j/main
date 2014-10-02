package sg.codengineers.ldo.parser;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Command.CommandType;

public class CommandImplTest {

	private static Command	testClass;

	@Before
	public void setUp() {
	}

	@After
	public void cleanUp() {
	}

	@Test
	public void testGetCommandType() {
		testClass = new CommandImpl("add test");
		assertEquals("get command type for add", CommandType.CREATE,
				testClass.getCommandType());

		testClass = new CommandImpl("retrieve 1");
		assertEquals("get command type for retrieve", CommandType.RETRIEVE,
				testClass.getCommandType());

		testClass = new CommandImpl("update 1");
		assertEquals("get command type for test", CommandType.UPDATE,
				testClass.getCommandType());

		testClass = new CommandImpl("delete 1");
		assertEquals("get command type for delete", CommandType.DELETE,
				testClass.getCommandType());

		testClass = new CommandImpl("show 1");
		assertEquals("get command type for show", CommandType.SHOW,
				testClass.getCommandType());

		testClass = new CommandImpl("aasdsa");
		assertEquals("get command type for invalid", CommandType.INVALID,
				testClass.getCommandType());
	}
	
	@Test
	public void testGetPrimaryOperand(){
		testClass = new CommandImpl("add test");
		assertEquals("get primary operand for add", "test",
				testClass.getPrimaryOperand());

		testClass = new CommandImpl("retrieve 1");
		assertEquals("get primary operand for retrieve", "1",
				testClass.getPrimaryOperand());

		testClass = new CommandImpl("update 1");
		assertEquals("get primary operand for test", "1",
				testClass.getPrimaryOperand());

		testClass = new CommandImpl("delete 1");
		assertEquals("get primary operand for delete", "1",
				testClass.getPrimaryOperand());

		testClass = new CommandImpl("show 1");
		assertEquals("get primary operand for show", null,
				testClass.getPrimaryOperand());

		testClass = new CommandImpl("aasdsa");
		assertEquals("get primary operand for invalid", null,
				testClass.getPrimaryOperand());
	}
}
