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
		assertEquals("checking invalid message", "Help argument expected\n",
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
				"Operand should follow additional argument name\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandAdd5() {
		Command obtainedCommand = testClass.parse("add hello --name world");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"name argument is invalid for create command type\n",
				obtainedCommand.getMessage());
	}

	@Test
	public void testParseToCommandAdd6() {
		Command obtainedCommand = testClass
				.parse("add hello -p high --name world");
		assertEquals("checking command type", CommandType.INVALID,
				obtainedCommand.getCommandType());
		assertEquals("checking invalid message",
				"name argument is invalid for create command type\n",
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
		Command obtainedCommand = testClass
				.parse("update 300 -time from 3pm to 4pm");
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
		assertEquals("checking argument operand", "from 3pm to 4pm",
				addArg.getOperand());
	}

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
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 0, obtainedDate.getMonth());
		assertEquals("checking for year", 1970 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 12, obtainedDate.getHours());
		assertEquals("checking for minute", 0, obtainedDate.getMinutes());
	}

	// hh:mma
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate17() {
		Date obtainedDate = testClass.parseToDate("12:00am");
		assertEquals("checking for day", 1, obtainedDate.getDate());
		assertEquals("checking for month", 0, obtainedDate.getMonth());
		assertEquals("checking for year", 1970 - 1900, obtainedDate.getYear());
		assertEquals("checking for hour", 0, obtainedDate.getHours());
		assertEquals("checking for minute", 0, obtainedDate.getMinutes());
	}

	// HH:mm
	@SuppressWarnings("deprecation")
	@Test
	public void testParseToDate18() {
		Date obtainedDate = testClass.parseToDate("24:01");
		assertEquals("checking for day", 2, obtainedDate.getDate());
		assertEquals("checking for month", 0, obtainedDate.getMonth());
		assertEquals("checking for year", 1970 - 1900, obtainedDate.getYear());
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

	private Field getAdditionalArguments(Command obtainedCommand)
			throws NoSuchFieldException, SecurityException {
		Field field = obtainedCommand.getClass().getDeclaredField(
				"_additionalArguments");
		field.setAccessible(true);
		return field;
	}
}
