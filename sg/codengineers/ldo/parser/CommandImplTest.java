package sg.codengineers.ldo.parser;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
	public void testGetCommandTypeForAdd() {
		testClass = new CommandImpl("add test");
		assertEquals("get command type for add", CommandType.CREATE,
				testClass.getCommandType());
	}

	@Test
	public void testGetCommandTypeForRetrieve() {
		testClass = new CommandImpl("retrieve 1");
		assertEquals("get command type for retrieve", CommandType.RETRIEVE,
				testClass.getCommandType());
	}

	@Test
	public void testGetCommandTypeForUpdate() {
		testClass = new CommandImpl("update 1");
		assertEquals("get command type for test", CommandType.UPDATE,
				testClass.getCommandType());
	}

	@Test
	public void testGetCommandTypeForDelete() {
		testClass = new CommandImpl("delete 1");
		assertEquals("get command type for delete", CommandType.DELETE,
				testClass.getCommandType());
	}

	@Test
	public void testGetCommandTypeForShow() {
		testClass = new CommandImpl("show 1");
		assertEquals("get command type for show", CommandType.SHOW,
				testClass.getCommandType());
	}

	@Test
	public void testGetCommandForInvalid() {
		testClass = new CommandImpl("aasdsa");
		assertEquals("get command type for invalid", CommandType.INVALID,
				testClass.getCommandType());
	}

	@Test
	public void testGetPrimaryOperandForAdd() {
		testClass = new CommandImpl("add test");
		assertEquals("get primary operand for add", "test",
				testClass.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForRetrieve() {
		testClass = new CommandImpl("retrieve 1");
		assertEquals("get primary operand for retrieve", "1",
				testClass.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForUpdate() {
		testClass = new CommandImpl("update 1");
		assertEquals("get primary operand for test", "1",
				testClass.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForDelete() {
		testClass = new CommandImpl("delete 1");
		assertEquals("get primary operand for delete", "1",
				testClass.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForShow() {
		testClass = new CommandImpl("show 1");
		assertEquals("get primary operand for show", null,
				testClass.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForInvalid() {
		testClass = new CommandImpl("aasdsa");
		assertEquals("get primary operand for invalid", null,
				testClass.getPrimaryOperand());
	}

	@Test
	public void testGetFirstWord() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method getFirstWord = testClass.getClass().getDeclaredMethod(
				"getFirstWord", String.class);
		getFirstWord.setAccessible(true);
		assertEquals("Getting first word from string", "first",
				getFirstWord.invoke(testClass, "first second third"));
	}

	@Test
	public void testRemoveFirstWord() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method removeFirstWord = testClass.getClass().getDeclaredMethod(
				"removeFirstWord", String.class);
		removeFirstWord.setAccessible(true);
		assertEquals("Removing first word from string", "second third",
				removeFirstWord.invoke(testClass, "first second third"));
	}
}
