package sg.codengineers.ldo.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	 * Tests for correct userInput.
	 * The following test is used to ensure that the user input is properly
	 * stored into the command Class
	 */
	@Test
	public void testGetUserInput() {
		Command obtainedCommand = testClass.parse("add test");
		assertEquals("Checking getUserInput", "add test",
				obtainedCommand.getUserInput());
	}

	@Test
	public void testGetUserInputWithAddArg() {
		Command obtainedCommand = testClass.parse("add test -d tester");
		assertEquals("Checking getUserInput with 1 additional argument",
				"add test -d tester", obtainedCommand.getUserInput());
	}

	@Test
	public void testGetUserInputWithAddArgs() {
		Command obtainedCommand = testClass
				.parse("add test -d testing -t cs2103");
		assertEquals("checking getUserInput with additional arguments",
				"add test -d testing -t cs2103", obtainedCommand.getUserInput());
	}

	@Test
	public void testGetUserInputBlank() {
		Command obtainedCommand = testClass.parse(" ");
		assertEquals("checking getUserInput with blanks", " ",
				obtainedCommand.getUserInput());
	}

	/**
	 * Tests for correct CommandType.
	 * The following tests are used to test if the correct CommandType is
	 * generated based on the available primary commands.
	 */

	// Create
	@Test
	public void testGetCommandTypeForAdd() {
		Command obtainedCommand = testClass.parse("add test");
		assertEquals("get command type for add", CommandType.CREATE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForAddDigit() {
		Command obtainedCommand = testClass.parse("add 1a");
		assertEquals("get command type for add with digit", CommandType.CREATE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForAddBlank() {
		Command obtainedCommand = testClass.parse("add ");
		assertEquals("get command type for adding blank", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	// Update
	@Test
	public void testGetCommandTypeForUpdate() {
		Command obtainedCommand = testClass.parse("update 1");
		assertEquals("get command type for update", CommandType.UPDATE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForUpdateChar() {
		Command obtainedCommand = testClass.parse("update a");
		assertEquals("get command type for update with a character",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForUpdateBlank() {
		Command obtainedCommand = testClass.parse("update ");
		assertEquals("get command tpe for update with blank",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForUpdateNegative() {
		Command obtainedCommand = testClass.parse("update -1");
		assertEquals("get command type for update with negative index",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForUpdateZero() {
		Command obtainedCommand = testClass.parse("update 0");
		assertEquals("get command type for update with 0", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	// Delete
	@Test
	public void testGetCommandTypeForDelete() {
		Command obtainedCommand = testClass.parse("delete 1");
		assertEquals("get command type for delete", CommandType.DELETE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForDeleteAll() {
		Command obtainedCommand = testClass.parse("delete all");
		assertEquals("get command type for delete all", CommandType.DELETE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForDeleteChar() {
		Command obtainedCommand = testClass.parse("delete a");
		assertEquals("get command type for delete a char", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForDeleteBlank() {
		Command obtainedCommand = testClass.parse("delete ");
		assertEquals("get command type for deleting blank",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForDeleteNegative() {
		Command obtainedCommand = testClass.parse("delete -3");
		assertEquals("get command type for deleting negative",
				CommandType.INVALID, obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForDeleteZero() {
		Command obtainedCommand = testClass.parse("delete 0");
		assertEquals("get command type for deleting zero", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	// Retrieve/Show/View
	@Test
	public void testGetCommandTypeForRetrieve() {
		Command obtainedCommand = testClass.parse("retrieve 1");
		assertEquals("get command type for retrieve", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForRetrieveAll() {
		Command obtainedCommand = testClass.parse("retrieve all");
		assertEquals("get command type for retrieve all", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	// TODO change to INVALID
	@Test
	public void testGetCommandTypeForRetrieveChar() {
		Command obtainedCommand = testClass.parse("retrieve a");
		assertEquals("get command type for retrieve a char",
				CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForRetrieveBlank() {
		Command obtainedCommand = testClass.parse("retrieve  ");
		assertEquals("get command type for retrieve blanks",
				CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForRetrieveNegative() {
		Command obtainedCommand = testClass.parse("retrieve -1");
		assertEquals("get command type for retrieve negative index",
				CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForRetrieveZero() {
		Command obtainedCommand = testClass.parse("retrieve 0");
		assertEquals("get command type for retrieve zero", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForView() {
		Command obtainedCommand = testClass.parse("view 1");
		assertEquals("get command type for view", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForViewAll() {
		Command obtainedCommand = testClass.parse("view all");
		assertEquals("get command type for view all", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	// TODO Change to invalid
	@Test
	public void testGetCommandTypeForViewChar() {
		Command obtainedCommand = testClass.parse("view a");
		assertEquals("get command type for view a char", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForViewBlank() {
		Command obtainedCommand = testClass.parse("view  ");
		assertEquals("get command type for view blanks", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForViewNegative() {
		Command obtainedCommand = testClass.parse("view -1");
		assertEquals("get command type for view negative index",
				CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForViewZero() {
		Command obtainedCommand = testClass.parse("view 0");
		assertEquals("get command type for view zero", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForShow() {
		Command obtainedCommand = testClass.parse("show 1");
		assertEquals("get command type for show", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForShowAll() {
		Command obtainedCommand = testClass.parse("show all");
		assertEquals("get command type for show all", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	// TODO change to invalid
	@Test
	public void testGetCommandTypeForShowChar() {
		Command obtainedCommand = testClass.parse("show a");
		assertEquals("get command type for show a char", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForShowBlank() {
		Command obtainedCommand = testClass.parse("show  ");
		assertEquals("get command type for show blanks", CommandType.RETRIEVE,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForShowNegative() {
		Command obtainedCommand = testClass.parse("show -1");
		assertEquals("get command type for show negative index",
				CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandTypeForShowZero() {
		Command obtainedCommand = testClass.parse("show 0");
		assertEquals("get command type for show zero", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	// Sync
	@Test
	public void testGetCommandForSync() {
		Command obtainedCommand = testClass.parse("sync");
		assertEquals("get comand type for sync", CommandType.SYNC,
				obtainedCommand.getCommandType());
	}

	// Search
	@Test
	public void testGetCommandForSearch() {
		Command obtainedCommand = testClass.parse("search hello");
		assertEquals("get command type for search", CommandType.SEARCH,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForSearchWithEmptyPrimary() {
		Command obtainedCommand = testClass.parse("search ");
		assertEquals("get command type for search with empty primary operand",
				CommandType.SEARCH, obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForSearchWithDigit() {
		Command obtainedCommand = testClass.parse("search 1");
		assertEquals("get command type for search with numeric parameter",
				CommandType.SEARCH, obtainedCommand.getCommandType());
	}

	// Help
	@Test
	public void testGetCommandForHelpAdd() {
		Command obtainedCommand = testClass.parse("add --help");
		assertEquals("get command type for add help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpUpdate() {
		Command obtainedCommand = testClass.parse("update --help");
		assertEquals("get command type for update help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpDelete() {
		Command obtainedCommand = testClass.parse("delete --help");
		assertEquals("get command type for delete help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpRetrieve() {
		Command obtainedCommand = testClass.parse("retrieve --help");
		assertEquals("get command type for retrieve help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpView() {
		Command obtainedCommand = testClass.parse("view --help");
		assertEquals("get command type for view help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpShow() {
		Command obtainedCommand = testClass.parse("show --help");
		assertEquals("get command type for show help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpSync() {
		Command obtainedCommand = testClass.parse("sync --help");
		assertEquals("get command type for sync help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpSearch() {
		Command obtainedCommand = testClass.parse("search --help");
		assertEquals("get command type for search help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelp() {
		Command obtainedCommand = testClass.parse("help");
		assertEquals("get command type for help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpHelp() {
		Command obtainedCommand = testClass.parse("help --help");
		assertEquals("get command type for help help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpUndo() {
		Command obtainedCommand = testClass.parse("undo --help");
		assertEquals("get command type for undo help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForHelpExit() {
		Command obtainedCommand = testClass.parse("exit --help");
		assertEquals("get command type for exit help", CommandType.HELP,
				obtainedCommand.getCommandType());
	}

	// Undo
	@Test
	public void testGetCommandForUndo() {
		Command obtainedCommand = testClass.parse("undo");
		assertEquals("get command type for undo", CommandType.UNDO,
				obtainedCommand.getCommandType());
	}

	// Exit
	@Test
	public void testGetCommandForExit() {
		Command obtainedCommand = testClass.parse("exit");
		assertEquals("get command type for exit", CommandType.EXIT,
				obtainedCommand.getCommandType());
	}

	// Invalid
	@Test
	public void testGetCommandForInvalid() {
		Command obtainedCommand = testClass.parse("aassdsa");
		assertEquals("get command type for invalid", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	@Test
	public void testGetCommandForEmptyPrimary() {
		Command obtainedCommand = testClass.parse("--help");
		assertEquals("get command type for empty primary", CommandType.INVALID,
				obtainedCommand.getCommandType());
	}

	/**
	 * Tests for correct primary operands.
	 * The following tests are used to ensure that the appropriate strings are
	 * allocated to the _primaryOperand field of each Command object.
	 */

	// Create
	@Test
	public void testGetPrimaryOperandForAdd() {
		Command obtainedCommand = testClass.parse("add test");
		assertEquals("get primary operand for add", "test",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForAddWithDigit() {
		Command obtainedCommand = testClass.parse("add 1");
		assertEquals("get primary operand for add with digit", "1",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForAddWithAddArg() {
		Command obtainedCommand = testClass.parse("add test -d tester");
		assertEquals("get primary operand for add with 1 additional argument",
				"test", obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForAddWithAddArgs() {
		Command obtainedCommand = testClass.parse("add test -d tester -t test");
		assertEquals("get primary operand for add with additional arguments",
				"test", obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForBlankAddWithAddArg() {
		Command obtainedCommand = testClass.parse("add -d tester");
		assertEquals(
				"get primary operand for blank add with additional arguments",
				"", obtainedCommand.getPrimaryOperand());
	}

	// Update
	@Test
	public void testGetPrimaryOperandForUpdate() {
		Command obtainedCommand = testClass.parse("update 1");
		assertEquals("get primary operand for update", "1",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForUpdateWithAddArg() {
		Command obtainedCommand = testClass.parse("update 1 -d tester");
		assertEquals(
				"get primary operand for update with 1 additional argument",
				"1", obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForUpdateWithAddArgs() {
		Command obtainedCommand = testClass
				.parse("update 1 -d tester -tag test");
		assertEquals(
				"get primary operand for update with additional arguments",
				"1", obtainedCommand.getPrimaryOperand());
	}

	// Delete
	@Test
	public void testGetPrimaryOperandForDelete() {
		Command obtainedCommand = testClass.parse("delete 1");
		assertEquals("get primary operand for delete", "1",
				obtainedCommand.getPrimaryOperand());
	}

	// Retrieve/Show/View
	@Test
	public void testGetPrimaryOperandForRetrieve() {
		Command obtainedCommand = testClass.parse("retrieve 1");
		assertEquals("get primary operand for retrieve", "1",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForRetrieveAll() {
		Command obtainedCommand = testClass.parse("show");
		assertEquals("get primary operand for show", "",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForShow() {
		Command obtainedCommand = testClass.parse("show all");
		assertEquals("get primary operand for show", "all",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForShowBlank() {
		Command obtainedCommand = testClass.parse("show");
		assertEquals("Get primary operand for show that has no operand", "",
				obtainedCommand.getPrimaryOperand());
	}

	// Sync
	@Test
	public void testGetPrimaryOperandForSync() {
		Command obtainedCommand = testClass.parse("sync");
		assertEquals("get primary operand for sync that has no operand", "",
				obtainedCommand.getPrimaryOperand());
	}

	// Search
	@Test
	public void testGetPrimaryOperandForSearch() {
		Command obtainedCommand = testClass.parse("search hello");
		assertEquals("get primary operand for search", "hello",
				obtainedCommand.getPrimaryOperand());
	}

	// Help
	@Test
	public void testGetPrimaryOperandForAddHelp() {
		Command obtainedCommand = testClass.parse("add --help");
		assertEquals("get primary operand for add help", "CREATE",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForUpdateHelp() {
		Command obtainedCommand = testClass.parse("update --help");
		assertEquals("get primary operand for update help", "UPDATE",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForDeleteHelp() {
		Command obtainedCommand = testClass.parse("delete --help");
		assertEquals("get primary operand for delete help", "DELETE",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForRetrieveHelp() {
		Command obtainedCommand = testClass.parse("retrieve --help");
		assertEquals("get primary operand for retrieve help", "RETRIEVE",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForShowHelp() {
		Command obtainedCommand = testClass.parse("show --help");
		assertEquals("get primary operand for show help", "RETRIEVE",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForViewHelp() {
		Command obtainedCommand = testClass.parse("view --help");
		assertEquals("get primary operand for view help", "RETRIEVE",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForSyncHelp() {
		Command obtainedCommand = testClass.parse("sync --help");
		assertEquals("get primary operand for sync help", "SYNC",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForSearchHelp() {
		Command obtainedCommand = testClass.parse("search --help");
		assertEquals("get primary oeprand for search help", "SEARCH",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForHelp() {
		Command obtainedCommand = testClass.parse("help");
		assertEquals("get primary operand for help", "",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForHelpHelp() {
		Command obtainedCommand = testClass.parse("help --help");
		assertEquals("get primary operand for help help", "HELP",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForUndoHelp() {
		Command obtainedCommand = testClass.parse("undo --help");
		assertEquals("get primary operand for undo help", "UNDO",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForExitHelp() {
		Command obtainedCommand = testClass.parse("exit --help");
		assertEquals("get primary operand for exit help", "EXIT",
				obtainedCommand.getPrimaryOperand());
	}

	// Exit
	@Test
	public void testGetPrimaryOperandForExit() {
		Command obtainedCommand = testClass.parse("exit");
		assertEquals("get primary operand for exit that has no operand", "",
				obtainedCommand.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForInvalid() {
		Command obtainedCommand = testClass.parse("aasdsa");
		assertNotNull("get primary operand for invalid",
				obtainedCommand.getPrimaryOperand());
	}

	/**
	 * Tests for appropriate AdditionalArgument List.
	 * The following tests ensure that the _additionalArguments field is not
	 * null. In the event that there are no additional arguments in the user's
	 * input, the List should be empty, and not null.
	 */

	@Test
	public void testAddCommandAdditionalArgumentsForNull() {
		Command obtainedCommand = testClass.parse("add test");
		assertNotNull(obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testRetrieveWithoutPrimaryCommandAdditionalArgumentsForNull() {
		Command obtainedCommand = testClass.parse("show");
		assertNotNull(obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testRetrieveCommandAdditionalArgumentsForNull() {
		Command obtainedCommand = testClass.parse("show all");
		assertNotNull(obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testUpdateCommandAdditionalArgumentsForNull() {
		Command obtainedCommand = testClass.parse("update 1");
		assertNotNull(obtainedCommand.getAdditionalArguments());
	}

	@Test
	public void testDeleteCommandAdditionalArgumentsForNull() {
		Command obtainedCommand = testClass.parse("delete 1");
		assertNotNull(obtainedCommand.getAdditionalArguments());
	}

	/**
	 * Tests for equals method.
	 * The following tests ensure that the equals method correctly checks for
	 * equality between Command Objects. Command Objects are deemed equal iff
	 * the object has the same user input string.
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
