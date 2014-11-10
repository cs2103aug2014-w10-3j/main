package sg.codengineers.ldo.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.AdditionalArgument.ArgumentType;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Parser;

//@author A0110741X
public class ParserImplTest {

	private Parser	testClass;

	@Before
	public void setUp() {
		testClass = new ParserImpl();
	}

	@After
	public void cleanUp() {
	}

	/**
	 * Test for parsing to command
	 */
	@Test
	public void testParseToCommandInvalid() {
		Command obtainedCommand = testClass.parse("      ");
		assertEquals("checking getUserInput", "      ",
				obtainedCommand.getUserInput());
		assertEquals("checking command type for blanks", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testParseToCommandInvalid1() {
		Command obtainedCommand = testClass.parse("asdasd");
		assertEquals("checking getUserInput", "asdasd",
				obtainedCommand.getUserInput());
		assertEquals("checking command type for asdasd", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testParseToCommandAdd() {
		Command obtainedCommand = testClass.parse("add hello");
		assertEquals("checking user input", "add hello",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.CREATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "hello",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandAdd1() {
		Command obtainedCommand = testClass.parse("add hello -ds tester");
		assertEquals("checking command type", CommandType.CREATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "hello",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking additional argument type",
				ArgumentType.DESCRIPTION, addArg.getArgumentType());
		assertEquals("checking additional argument operand", "tester",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandAdd2() {
		Command obtainedCommand = testClass
				.parse("add hello -ds tester -tag cs2103T");
		assertEquals("checking command type", CommandType.CREATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "hello",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking additional argument type",
				ArgumentType.DESCRIPTION, addArg.getArgumentType());
		assertEquals("checking additional argument operand", "tester",
				addArg.getOperand());
		addArg = toCheck.next();
		assertEquals("checking additional argument type",
				ArgumentType.TAG, addArg.getArgumentType());
		assertEquals("checking additional argument operand", "cs2103T",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandAdd3() {
		Command obtainedCommand = testClass.parse("add ");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Empty operand for create command not supported.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandAdd4() {
		Command obtainedCommand = testClass.parse("add --name");
		assertEquals("Checking user input", "add --name",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Operand should follow additional argument name.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandAdd5() {
		Command obtainedCommand = testClass.parse("add hello --name world");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"name argument is invalid for create command type.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandAdd6() {
		Command obtainedCommand = testClass
				.parse("add hello -p high --name world");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"name argument is invalid for create command type.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandUpdate() {
		Command obtainedCommand = testClass.parse("update 1");
		assertEquals("checking user input", "update 1",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.UPDATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "1",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandUpdate1() {
		Command obtainedCommand = testClass.parse("update 1 --name new name");
		assertEquals("checking command type", CommandType.UPDATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "1",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking argument type", ArgumentType.NAME,
				addArg.getArgumentType());
		assertEquals("checking argument operand", "new name",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandUpdate2() {
		Command obtainedCommand = testClass.parse("update 4 -p high");
		assertEquals("checking command type", CommandType.UPDATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "4",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking argument type", ArgumentType.PRIORITY,
				addArg.getArgumentType());
		assertEquals("checking argument operand", "high", addArg.getOperand());
	}

	@Test
	public void testParseToCommandUpdate3() {
		Command obtainedCommand = testClass.parse("update 26 -ds new desc");
		assertEquals("checking command type", CommandType.UPDATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "26",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking argument type", ArgumentType.DESCRIPTION,
				addArg.getArgumentType());
		assertEquals("checking argument operand", "new desc",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandUpdate4() {
		Command obtainedCommand = testClass.parse("update 56 -dl 10 nov 2014");
		assertEquals("checking command type", CommandType.UPDATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "56",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking argument type", ArgumentType.DEADLINE,
				addArg.getArgumentType());
		assertEquals("checking argument operand", "10 nov 2014",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandUpdate5() {
		Command obtainedCommand = testClass.parse("update 3 --done");
		assertEquals("checking command type", CommandType.UPDATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "3",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking argument type", ArgumentType.TAG,
				addArg.getArgumentType());
		assertEquals("checking argument operand", "done",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandUpdate6() {
		Command obtainedCommand = testClass
				.parse("update 1 --time from 3pm to 4pm");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"\"from 3pm to 4pm\" is not a valid operand for time.\n",
				obtainedCommand.getMessage());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandUpdate7() {
		Command obtainedCommand = testClass.parse("update a");
		assertEquals("Checking user input", "update a",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand for update should contain numbers.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandUpdate8() {
		Command obtainedCommand = testClass.parse("update ");
		assertEquals("Checking user input", "update ",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Empty operand for update command not supported.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandUpdate9() {
		Command obtainedCommand = testClass.parse("update -1");
		assertEquals("Checking user input", "update -1",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Invalid additional argument entered.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandUpdate10() {
		Command obtainedCommand = testClass.parse("update 0");
		assertEquals("Checking user input", "update 0",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand should not be less than 1.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandUpdate11() {
		Command obtainedCommand = testClass.parse("update 300 --time 3pm 4pm");
		assertEquals("checking command type", CommandType.UPDATE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "300",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking argument type", ArgumentType.TIME,
				addArg.getArgumentType());
		assertEquals("checking argument operand", "3pm 4pm",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandUpdate12() {
		Command obtainedCommand = testClass
				.parse("update 300 --time 3pm 4pm --deadline 10 Nov 2014");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals(
				"checking invalid message",
				"Not possible to set both deadline and time range for the same task.\n",
				obtainedCommand.getMessage());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandDelete() {
		Command obtainedCommand = testClass.parse("delete 1");
		assertEquals("checking user input", "delete 1",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.DELETE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "1",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandDelete1() {
		Command obtainedCommand = testClass.parse("delete all");
		assertEquals("checking user input", "delete all",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.DELETE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "all",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandDelete2() {
		Command obtainedCommand = testClass.parse("delete a");
		assertEquals("checking user input", "delete a",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand for delete should contain numbers.\n",
				obtainedCommand.getMessage());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandDelete3() {
		Command obtainedCommand = testClass.parse("delete ");
		assertEquals("Checking user input", "delete ",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Empty operand for delete command not supported.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandDelete4() {
		Command obtainedCommand = testClass.parse("delete -3");
		assertEquals("Checking user input", "delete -3",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Invalid additional argument entered.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandDelete5() {
		Command obtainedCommand = testClass.parse("delete 0");
		assertEquals("Checking user input", "delete 0",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand should not be less than 1.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandDelete6() {
		Command obtainedCommand = testClass.parse("delete --name hello");
		assertEquals("Checking user input", "delete --name hello",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"name argument is invalid for delete command type.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandRetrieve() {
		Command obtainedCommand = testClass.parse("retrieve 1");
		assertEquals("checking user input", "retrieve 1",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "1",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve1() {
		Command obtainedCommand = testClass.parse("show 11");
		assertEquals("checking user input", "show 11",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "11",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve2() {
		Command obtainedCommand = testClass.parse("view 50");
		assertEquals("checking user input", "view 50",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "50",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve3() {
		Command obtainedCommand = testClass.parse("retrieve all");
		assertEquals("checking user input", "retrieve all",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "all",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve4() {
		Command obtainedCommand = testClass.parse("view all");
		assertEquals("checking user input", "view all",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "all",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve5() {
		Command obtainedCommand = testClass.parse("show all");
		assertEquals("checking user input", "show all",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "all",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve6() {
		Command obtainedCommand = testClass.parse("show");
		assertEquals("checking user input", "show",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve7() {
		Command obtainedCommand = testClass.parse("show --tag hello");
		assertEquals("Checking user input", "show --tag hello",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"tag argument is invalid for retrieve command type.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandRetrieve8() {
		Command obtainedCommand = testClass.parse("retrieve a");
		assertEquals("checking user input", "retrieve a",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand for retrieve should contain numbers.\n",
				obtainedCommand.getMessage());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve9() {
		Command obtainedCommand = testClass.parse("view a");
		assertEquals("checking user input", "view a",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand for retrieve should contain numbers.\n",
				obtainedCommand.getMessage());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve10() {
		Command obtainedCommand = testClass.parse("show a");
		assertEquals("checking user input", "show a",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand for retrieve should contain numbers.\n",
				obtainedCommand.getMessage());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandRetrieve11() {
		Command obtainedCommand = testClass.parse("retrieve -33");
		assertEquals("Checking user input", "retrieve -33",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Invalid additional argument entered.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandRetrieve12() {
		Command obtainedCommand = testClass.parse("view -183");
		assertEquals("Checking user input", "view -183",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Invalid additional argument entered.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandRetrieve13() {
		Command obtainedCommand = testClass.parse("show -593");
		assertEquals("Checking user input", "show -593",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Invalid additional argument entered.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandRetrieve14() {
		Command obtainedCommand = testClass.parse("retrieve 0");
		assertEquals("Checking user input", "retrieve 0",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand should not be less than 1.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandRetrieve15() {
		Command obtainedCommand = testClass.parse("view 0");
		assertEquals("Checking user input", "view 0",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand should not be less than 1.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandRetrieve16() {
		Command obtainedCommand = testClass.parse("show 0");
		assertEquals("Checking user input", "show 0",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"Primary operand should not be less than 1.\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandSync() {
		Command obtainedCommand = testClass.parse("sync");
		assertEquals("Checking user input", "sync",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.SYNC,
				obtainedCommand.getCommandType());
		assertTrue("checking primary operand", obtainedCommand
				.getPrimaryOperand().isEmpty());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandSync1() {
		Command obtainedCommand = testClass.parse("sync 1");
		assertEquals("Checking user input", "sync 1",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.SYNC,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "1",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandSync2() {
		Command obtainedCommand = testClass.parse("sync a");
		assertEquals("Checking user input", "sync a",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.SYNC,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "a",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandSearch() {
		Command obtainedCommand = testClass.parse("search hello there");
		assertEquals("checking command type", CommandType.SEARCH,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "hello there",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandSearch1() {
		Command obtainedCommand = testClass.parse("search ");
		assertEquals("checking command type", CommandType.SEARCH,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandSearch2() {
		Command obtainedCommand = testClass.parse("search 96489748");
		assertEquals("checking command type", CommandType.SEARCH,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "96489748",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandSearch3() {
		Command obtainedCommand = testClass.parse("search -desc this");
		assertEquals("checking command type", CommandType.SEARCH,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking additional argument type",
				ArgumentType.DESCRIPTION, addArg.getArgumentType());
		assertEquals("checking additional argument operand", "this",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandSearch4() {
		Command obtainedCommand = testClass.parse("search --priority high");
		assertEquals("checking command type", CommandType.SEARCH,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking additional argument type",
				ArgumentType.PRIORITY, addArg.getArgumentType());
		assertEquals("checking additional argument operand", "high",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandSearch5() {
		Command obtainedCommand = testClass
				.parse("search --tag cs2103T -dd 10/11/14");
		assertEquals("checking command type", CommandType.SEARCH,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking additional argument type",
				ArgumentType.TAG, addArg.getArgumentType());
		assertEquals("checking additional argument operand", "cs2103T",
				addArg.getOperand());
		addArg = toCheck.next();
		assertEquals("checking additional argument type",
				ArgumentType.DEADLINE, addArg.getArgumentType());
		assertEquals("checking additional argument operand", "10/11/14",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandSearch6() {
		Command obtainedCommand = testClass.parse("search --time 23:59");
		assertEquals("checking command type", CommandType.SEARCH,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
		Iterator<AdditionalArgument> toCheck = obtainedCommand
				.getAdditionalArguments();
		AdditionalArgument addArg = toCheck.next();
		assertEquals("checking additional argument type",
				ArgumentType.TIME, addArg.getArgumentType());
		assertEquals("checking additional argument operand", "23:59",
				addArg.getOperand());
	}

	@Test
	public void testParseToCommandHelp() {
		Command obtainedCommand = testClass.parse("help");
		assertEquals("checking user input", "help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertTrue("checking empty primary operand",
				obtainedCommand.getPrimaryOperand().isEmpty());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp1() {
		Command obtainedCommand = testClass.parse("help --help");
		assertEquals("checking user input", "help --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "help",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp2() {
		Command obtainedCommand = testClass.parse("add --help");
		assertEquals("checking user input", "add --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "create",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp3() {
		Command obtainedCommand = testClass.parse("update --help");
		assertEquals("checking user input", "update --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "update",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp4() {
		Command obtainedCommand = testClass.parse("delete --help");
		assertEquals("checking user input", "delete --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "delete",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp5() {
		Command obtainedCommand = testClass.parse("retrieve --help");
		assertEquals("checking user input", "retrieve --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "retrieve",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp6() {
		Command obtainedCommand = testClass.parse("view --help");
		assertEquals("checking user input", "view --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "retrieve",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp7() {
		Command obtainedCommand = testClass.parse("show --help");
		assertEquals("checking user input", "show --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "retrieve",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp8() {
		Command obtainedCommand = testClass.parse("sync --help");
		assertEquals("checking user input", "sync --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "sync",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp9() {
		Command obtainedCommand = testClass.parse("search --help");
		assertEquals("checking user input", "search --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "search",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp10() {
		Command obtainedCommand = testClass.parse("undo --help");
		assertEquals("checking user input", "undo --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "undo",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandHelp11() {
		Command obtainedCommand = testClass.parse("exit --help");
		assertEquals("checking user input", "exit --help",
				obtainedCommand.getUserInput());
		assertEquals("checking command type", CommandType.HELP,
				obtainedCommand.getCommandType());
		assertEquals("checking primary operand", "exit",
				obtainedCommand.getPrimaryOperand());
		assertTrue("checking message", obtainedCommand.getMessage().isEmpty());
		assertNotNull("checking iterator for nullity",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandUndo() {
		Command obtainedCommand = testClass.parse("undo");
		assertEquals("checking command type", CommandType.UNDO,
				obtainedCommand.getCommandType());
		assertTrue("checking empty primary operand", obtainedCommand
				.getPrimaryOperand().isEmpty());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandUndo1() {
		Command obtainedCommand = testClass.parse("undo --name hi");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertTrue("checking empty primary operand", obtainedCommand
				.getPrimaryOperand().isEmpty());
		assertEquals("checking invalid message",
				"name argument is invalid for undo command type.\n",
				obtainedCommand.getMessage());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandExit() {
		Command obtainedCommand = testClass.parse("exit");
		assertEquals("checking command type", CommandType.EXIT,
				obtainedCommand.getCommandType());
		assertTrue("checking empty primary operand", obtainedCommand
				.getPrimaryOperand().isEmpty());
		assertTrue("checking empty message", obtainedCommand.getMessage()
				.isEmpty());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testParseToCommandExit1() {
		Command obtainedCommand = testClass.parse("exit --desc hi");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertTrue("checking empty primary operand", obtainedCommand
				.getPrimaryOperand().isEmpty());
		assertEquals("checking invalid message",
				"description argument is invalid for exit command type.\n",
				obtainedCommand.getMessage());
		assertNotNull("checking for null iterator",
				obtainedCommand.getAdditionalArguments());
	}

	/**
	 * Testing equals method
	 */

	@Test
	public void testEqualsWithNull() {
		Command obtainedCommand = testClass.parse("add test");
		assertFalse("Checking if equals compares correctly with null",
				obtainedCommand.equals(null));
	}

	@Test
	public void testEqualWithSelf() {
		Command obtainedCommand = testClass.parse("add test");
		assertTrue("Checking if equals compares correctly with self",
				obtainedCommand.equals(obtainedCommand));
	}

	@Test
	public void testEqualsWithNonCommandObjects() {
		Command obtainedCommand = testClass.parse("add test");
		assertFalse("Checking if equals compares correctly with other objects",
				obtainedCommand.equals("test"));
	}

	@Test
	public void testEqualsWithAddCommand() {
		Command obtainedCommand = testClass.parse("add test");
		assertTrue(
				"Checking if equals compares correctly with same object for add",
				obtainedCommand.equals(testClass.parse("add test")));
	}

	@Test
	public void testEqualsWithUpdateCommand() {
		Command obtainedCommand = testClass.parse("update 1");
		assertTrue(
				"Checking if equals compares correctly with same object for update",
				obtainedCommand.equals(testClass.parse("update 1")));
	}

	@Test
	public void testEqualsWithDeleteCommand() {
		Command obtainedCommand = testClass.parse("delete 1");
		assertTrue(
				"Checking if equals compares correctly with same object for delete",
				obtainedCommand.equals(testClass.parse("delete 1")));
	}

	@Test
	public void testEqualsWithRetrieveCommand() {
		Command obtainedCommand = testClass.parse("retrieve 1");
		assertTrue(
				"Checking if equals compares correctly with same object for retrieve",
				obtainedCommand.equals(testClass.parse("retrieve 1")));
	}

	@Test
	public void testEqualsWithSyncCommand() {
		Command obtainedCommand = testClass.parse("sync");
		assertTrue(
				"Checking if equals compares correctly with same object for sync",
				obtainedCommand.equals(testClass.parse("sync")));
	}

	@Test
	public void testEqualsWithExitCommand() {
		Command obtainedCommand = testClass.parse("exit");
		assertTrue(
				"Checking if equals compares correctly with same object for exit",
				obtainedCommand.equals(testClass.parse("exit")));
	}

	@Test
	public void testEqualsWithSearchCommand() {
		Command obtainedCommand = testClass.parse("search hello");
		assertTrue(
				"Checking if equals compares correctly with same object for saerch",
				obtainedCommand.equals(testClass.parse("search hello")));
	}

	/**
	 * Test for parsing to date
	 */

	// dd MMM yy hha
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate() {
		Date obtainedDate = testClass.parseToDate("1 Jan 14 2pm");
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 0, obtainedDate.getMonth());
		assertEquals("checking for year", 2014 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 14, obtainedDate.getHours());
		assertEquals("checking for minute", 00, obtainedDate.getMinutes());
	}

	// dd MMM yyyy hha
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate1() {
		Date obtainedDate = testClass.parseToDate("11 jan 1993 3am");
		assertEquals("checking for day", 11, obtainedDate.getDate());
		assertEquals("checking for month", 0, obtainedDate.getMonth());
		assertEquals("checking for year", 1993 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 03, obtainedDate.getHours());
		assertEquals("checking for minute", 00, obtainedDate.getMinutes());
	}

	// dd MMM yy hh:mma
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate2() {
		Date obtainedDate = testClass.parseToDate("17 aug 92 6:03am");
		assertEquals("checking for day", 17, obtainedDate.getDate());
		assertEquals("checking for month", 7, obtainedDate.getMonth());
		assertEquals("checking for year", 1992 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 06, obtainedDate.getHours());
		assertEquals("checking for minute", 03, obtainedDate.getMinutes());
	}

	// dd MMM yyyy hh:mma
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate3() {
		Date obtainedDate = testClass.parseToDate("17 Aug 1993 4:03pm");
		assertEquals("checking for day", 17, obtainedDate.getDate());
		assertEquals("checking for month", 7, obtainedDate.getMonth());
		assertEquals("checking for year", 1993 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 16, obtainedDate.getHours());
		assertEquals("checking for minute", 03, obtainedDate.getMinutes());
	}

	// dd MMM yy HH:mm
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate4() {
		Date obtainedDate = testClass.parseToDate("16 oct 1996 18:38");
		assertEquals("checking for day", 16, obtainedDate.getDate());
		assertEquals("checking for month", 9, obtainedDate.getMonth());
		assertEquals("checking for year", 1996 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 18, obtainedDate.getHours());
		assertEquals("checking for minute", 38, obtainedDate.getMinutes());
	}

	// dd MMM yyyy HH:mm
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate5() {
		Date obtainedDate = testClass.parseToDate("11 nov 2014 14:40");
		assertEquals("checking for day", 11, obtainedDate.getDate());
		assertEquals("checking for month", 10, obtainedDate.getMonth());
		assertEquals("checking for year", 2014 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 14, obtainedDate.getHours());
		assertEquals("checking for minute", 40, obtainedDate.getMinutes());
	}

	// dd/MM/yy hha
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate6() {
		Date obtainedDate = testClass.parseToDate("26/11/68 9pm");
		assertEquals("checking for day", 26, obtainedDate.getDate());
		assertEquals("checking for month", 10, obtainedDate.getMonth());
		assertEquals("checking for year", 1968 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 21, obtainedDate.getHours());
		assertEquals("checking for minute", 00, obtainedDate.getMinutes());
	}

	// dd/MM/yyyy hha
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate7() {
		Date obtainedDate = testClass.parseToDate("26/08/1968 5am");
		assertEquals("checking for day", 26, obtainedDate.getDate());
		assertEquals("checking for month", 07, obtainedDate.getMonth());
		assertEquals("checking for year", 1968 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 05, obtainedDate.getHours());
		assertEquals("checking for minute", 00, obtainedDate.getMinutes());
	}

	// dd/MM/yy hh:mma
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate8() {
		Date obtainedDate = testClass.parseToDate("15/2/32 7:32am");
		assertEquals("checking for day", 15, obtainedDate.getDate());
		assertEquals("checking for month", 1, obtainedDate.getMonth());
		assertEquals("checking for year", 2032 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 07, obtainedDate.getHours());
		assertEquals("checking for minute", 32, obtainedDate.getMinutes());
	}

	// dd/MM/yyyy hh:mma
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate9() {
		Date obtainedDate = testClass.parseToDate("29/02/2012 3:33pm");
		assertEquals("checking for day", 29, obtainedDate.getDate());
		assertEquals("checking for month", 1, obtainedDate.getMonth());
		assertEquals("checking for year", 2012 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 15, obtainedDate.getHours());
		assertEquals("checking for minute", 33, obtainedDate.getMinutes());
	}

	// dd/MM/yy HH:mm
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate10() {
		Date obtainedDate = testClass.parseToDate("29/02/13 15:55");
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 2, obtainedDate.getMonth());
		assertEquals("checking for year", 2013 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 15, obtainedDate.getHours());
		assertEquals("checking for minute", 55, obtainedDate.getMinutes());
	}

	// dd/MM/yyyy HH:mm
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate11() {
		Date obtainedDate = testClass.parseToDate("32/1/54 16:61");
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 1, obtainedDate.getMonth());
		assertEquals("checking for year", 1954 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 17, obtainedDate.getHours());
		assertEquals("checking for minute", 01, obtainedDate.getMinutes());
	}

	// dd MMM yy
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate12() {
		Date obtainedDate = testClass.parseToDate("32 mar 28");
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 3, obtainedDate.getMonth());
		assertEquals("checking for year", 2028 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 23, obtainedDate.getHours());
		assertEquals("checking for minute", 59, obtainedDate.getMinutes());
	}

	// dd MMM yyyy
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate13() {
		Date obtainedDate = testClass.parseToDate("31 apr 2195");
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 4, obtainedDate.getMonth());
		assertEquals("checking for year", 2195 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 23, obtainedDate.getHours());
		assertEquals("checking for minute", 59, obtainedDate.getMinutes());
	}

	// dd/MM/yy
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate14() {
		Date obtainedDate = testClass.parseToDate("32/5/34 ");
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 5, obtainedDate.getMonth());
		assertEquals("checking for year", 2034 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 23, obtainedDate.getHours());
		assertEquals("checking for minute", 59, obtainedDate.getMinutes());
	}

	// dd/MM/yyyy
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate15() {
		Date obtainedDate = testClass.parseToDate("31/6/5672");
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 6, obtainedDate.getMonth());
		assertEquals("checking for year", 5672 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 23, obtainedDate.getHours());
		assertEquals("checking for minute", 59, obtainedDate.getMinutes());
	}

	// hha
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate16() {
		Date obtainedDate = testClass.parseToDate("12pm");
		Date today = new Date();
		assertEquals("checking for day", today.getDate(),
				obtainedDate.getDate());
		assertEquals("checking for month", today.getMonth(),
				obtainedDate.getMonth());
		assertEquals("checking for year", today.getYear(),
				obtainedDate.getYear());
		assertEquals("checking for hour", 12, obtainedDate.getHours());
		assertEquals("checking for minute", 0, obtainedDate.getMinutes());
	}

	// hh:mma
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate17() {
		Date obtainedDate = testClass.parseToDate("12:00am");
		Date today = new Date();
		assertEquals("checking for day", today.getDate(),
				obtainedDate.getDate());
		assertEquals("checking for month", today.getMonth(),
				obtainedDate.getMonth());
		assertEquals("checking for year", today.getYear(),
				obtainedDate.getYear());
		assertEquals("checking for hour", 0, obtainedDate.getHours());
		assertEquals("checking for minute", 0, obtainedDate.getMinutes());
	}

	// HH:mm
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate18() {
		Date obtainedDate = testClass.parseToDate("24:01");
		Date today = new Date();
		assertEquals("checking for day", today.getDate(),
				obtainedDate.getDate());
		assertEquals("checking for month", today.getMonth(),
				obtainedDate.getMonth());
		assertEquals("checking for year", today.getYear(),
				obtainedDate.getYear());
		assertEquals("checking for hour", 0, obtainedDate.getHours());
		assertEquals("checking for minute", 1, obtainedDate.getMinutes());
	}

	/**
	 * Tests for private helper methods
	 * The following tests are to ensure that all the helper methods in the
	 * Command class is working properly. The method that each test is
	 * concerned with is indicated above them with in a line of comment.
	 */

	// void populateCmdMap()
	@Test
	public void testPopulateCmdMap() throws Exception {
		testClass.parse("add test");
		Field cmdMap = testClass.getClass().getDeclaredField("_cmdMap");
		cmdMap.setAccessible(true);
		assertNotNull("checking if cmdMap is null", cmdMap);

	}

	// String getFirstWord(String)
	@Test
	public void testGetFirstWord() throws Exception {
		Method getFirstWord = getMethodFromClass("getFirstWord", String.class);
		assertEquals("Getting first word from string", "first",
				getFirstWord.invoke(testClass, "first second third"));
	}

	// String removeFirstWord(String)
	@Test
	public void testRemoveFirstWord() throws Exception {
		Method removeFirstWord = getMethodFromClass("removeFirstWord",
				String.class);
		assertEquals("Removing first word from string", "second third",
				removeFirstWord.invoke(testClass, "first second third"));
	}

	// String[] splitToArguments(String)
	@Test
	public void testSplitToArgumentsWithNothing() throws Exception {
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] {};
		String[] actual = (String[]) splitToArguments.invoke(testClass,
				"");
		assertArrayEquals(
				"Testing splitToArguments for no additional arguments",
				expected, actual);
	}

	@Test
	public void testSplitToArgumentsWithOneWord() throws Exception {
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] { "tag test" };
		String[] actual = (String[]) splitToArguments
				.invoke(testClass, "--tag test");
		assertArrayEquals("Testing splitToArguments with one word before dash",
				expected, actual);
	}

	@Test
	public void testSplitToArgumentsWithMultipleWords() throws Exception {
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] { "tag important" };
		String[] actual = (String[]) splitToArguments.invoke(testClass,
				"--tag important");
		assertArrayEquals(
				"Testing splitToArguments with multiple words before dash",
				expected, actual);
	}

	@Test
	public void testSplitToArgumentsWithSingleDash() throws Exception {
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] { "a single" };
		String[] actual = (String[]) splitToArguments.invoke(testClass,
				"-a single");
		assertArrayEquals(
				"Testing splitToArguments with Single dash in string",
				expected, actual);
	}

	@Test
	public void testSplitToArgumentsWithTwoDashes() throws Exception {
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] { "a multiple dashes", "tag test" };
		String[] actual = (String[]) splitToArguments.invoke(testClass,
				"-a multiple dashes --tag test");
		assertArrayEquals(
				"Testing splitToArguments with two sets of dashes in string",
				expected, actual);

	}

	@Test
	public void testNoInput() throws Exception {
		Command obtainedCommand = testClass.parse("");
		assertEquals("testing no input", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testBlankInput() throws Exception {
		Command obtainedCommand = testClass.parse(" ");
		assertEquals("testing blank input", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testMultipleBlanksInput() throws Exception {
		Command obtainedCommand = testClass.parse("          ");
		assertEquals("testing multiple blanks in input", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testAddWithEmptyPrimary() throws Exception {
		Command obtainedCommand = testClass.parse("add ");
		assertEquals("testing add with empty primary operand",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testUpdateWithEmptyPrimary() throws Exception {
		Command obtainedCommand = testClass.parse("update ");
		assertEquals("testing update with empty primary operand",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testDeleteWithEmptyPrimary() throws Exception {
		Command obtainedCommand = testClass.parse("delete ");
		assertEquals("testing delete with empty primary operand",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testUpdateWithNonDigit() throws Exception {
		Command obtainedCommand = testClass.parse("update a");
		assertEquals("testing update with empty primary operand",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testDeleteWithNonDigit() throws Exception {
		Command obtainedCommand = testClass.parse("delete a");
		assertEquals("testing delete with non digit primary operand",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	/**
	 * Gets a method from the testClass with the specified method name and
	 * parameter type and sets it to be accessible
	 * 
	 * @param methodName
	 *            The name of the method to get from the class
	 * @param parameterType
	 *            The parameter array
	 * @return the Method object for the method of this class matching the
	 *         specified name and parameters
	 * @throws NoSuchMethodException
	 *             if a matching method is not found
	 * @throws NullPointerException
	 *             if name is null
	 * @throws SecurityException
	 *             If a security manager, s, is present and any of the following
	 *             conditions is met: invocation of s.checkMemberAccess(this,
	 *             Member.DECLARED) denies access to the declared method the
	 *             caller's class loader is not the same as or an ancestor of
	 *             the class loader for the current class and invocation of
	 *             s.checkPackageAccess() denies access to the package of this
	 *             class
	 */
	private Method getMethodFromClass(String methodName,
			Class<?>... parameterType) throws NoSuchMethodException,
			NullPointerException, SecurityException {
		Method method = testClass.getClass().getDeclaredMethod(methodName,
				parameterType);
		method.setAccessible(true);
		return method;

	}
}
