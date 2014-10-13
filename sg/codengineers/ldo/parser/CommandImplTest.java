package sg.codengineers.ldo.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

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

	/**
	 * Tests for correct userInput.
	 * The following test is used to ensure that the user input is properly
	 * stored into the command Class
	 */
	@Test
	public void testGetUserInput() throws Exception {
		testClass = new CommandImpl("add test");
		assertEquals("Checking getUserInput", "add test",
				testClass.getUserInput());
	}

	/**
	 * Tests for correct CommandType.
	 * The following tests are used to test if the correct CommandType is
	 * generated based on the available primary commands.
	 */

	@Test
	public void testGetCommandTypeForAdd() {
		testClass = new CommandImpl("add test");
		assertEquals("get command type for add", CommandType.CREATE,
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
	public void testGetCommandTypeForRetrieve() {
		testClass = new CommandImpl("retrieve 1");
		assertEquals("get command type for retrieve", CommandType.RETRIEVE,
				testClass.getCommandType());
	}

	@Test
	public void testGetCommandTypeForView() {
		testClass = new CommandImpl("view 1");
		assertEquals("get command type for view", CommandType.RETRIEVE,
				testClass.getCommandType());
	}

	@Test
	public void testGetCommandTypeForShow() {
		testClass = new CommandImpl("show 1");
		assertEquals("get command type for show", CommandType.RETRIEVE,
				testClass.getCommandType());
	}

	@Test
	public void testGetCommandForInvalid() {
		testClass = new CommandImpl("aasdsa");
		assertEquals("get command type for invalid", CommandType.INVALID,
				testClass.getCommandType());
	}

	/**
	 * Tests for correct primary operands.
	 * The following tests are used to ensure that the appropriate strings are
	 * allocated to the _primaryOperand field of each Command object.
	 */
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
		testClass = new CommandImpl("show all");
		assertEquals("get primary operand for show", "all",
				testClass.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForShowBlank() {
		testClass = new CommandImpl("show");
		assertEquals("Get primary operand for show that has no operand", "",
				testClass.getPrimaryOperand());
	}

	@Test
	public void testGetPrimaryOperandForInvalid() {
		testClass = new CommandImpl("aasdsa");
		assertEquals("get primary operand for invalid", null,
				testClass.getPrimaryOperand());
	}

	/**
	 * Tests for appropriate AdditionalArgument List.
	 * The following tests ensure that the _additionalArguments field is not
	 * null. In the event that there are no additional arguments in the user's
	 * input, the List should be empty, and not null.
	 */

	@Test
	public void testAddCommandAdditionalArgumentsForNull() throws Exception {
		testClass = new CommandImpl("add test");
		Field additionalArguments = testClass.getClass().getDeclaredField(
				"_additionalArguments");
		additionalArguments.setAccessible(true);
		assertNotNull(additionalArguments);
	}

	@Test
	public void testRetrieveWithoutPrimaryCommandAdditionalArgumentsForNull()
			throws Exception {
		testClass = new CommandImpl("show");
		Field additionalArguments = testClass.getClass().getDeclaredField(
				"_additionalArguments");
		additionalArguments.setAccessible(true);
		assertNotNull(additionalArguments);
	}

	@Test
	public void testRetrieveCommandAdditionalArgumentsForNull()
			throws Exception {
		testClass = new CommandImpl("show all");
		Field additionalArguments = testClass.getClass().getDeclaredField(
				"_additionalArguments");
		additionalArguments.setAccessible(true);
		assertNotNull(additionalArguments);
	}

	@Test
	public void testUpdateCommandAdditionalArgumentsForNull() throws Exception {
		testClass = new CommandImpl("update 1");
		Field additionalArguments = testClass.getClass().getDeclaredField(
				"_additionalArguments");
		additionalArguments.setAccessible(true);
		assertNotNull(additionalArguments);
	}

	@Test
	public void testDeleteCommandAdditionalArgumentsForNull() throws Exception {
		testClass = new CommandImpl("delete 1");
		Field additionalArguments = testClass.getClass().getDeclaredField(
				"_additionalArguments");
		additionalArguments.setAccessible(true);
		assertNotNull(additionalArguments);
	}

	/**
	 * Tests for equals method.
	 * The following tests ensure that the equals method correctly checks for
	 * equality between Command Objects. Command Objects are deemed equal iff
	 * the object has the same user input string.
	 */

	@Test
	public void testEqualsWithNull() {
		testClass = new CommandImpl("add test");
		assertFalse("Checking if equals compares correctly with null",
				testClass.equals(null));
	}

	@Test
	public void testEqualWithSelf() {
		testClass = new CommandImpl("add test");
		assertTrue("Checking if equals compares correctly with self",
				testClass.equals(testClass));
	}

	@Test
	public void testEqualsWithNonCommandObjects() {
		testClass = new CommandImpl("add test");
		assertFalse("Checking if equals compares correctly with other objects",
				testClass.equals("test"));
	}

	@Test
	public void testEqualsWithSameCommand() {
		testClass = new CommandImpl("add test");
		assertTrue("Checking if equals compares correctly with same object",
				testClass.equals(new CommandImpl("add test")));
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
		testClass = new CommandImpl("add test");
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

	// String RemoveFirstWord(String)

	// String removeFirstWord(String)
	@Test
	public void testRemoveFirstWord() throws Exception {
		Method removeFirstWord = getMethodFromClass("removeFirstWord",
				String.class);
		assertEquals("Removing first word from string", "second third",
				removeFirstWord.invoke(testClass, "first second third"));
	}

	// String[] splitToArguments(String);
	// String[] splitToArguments(String)
	@Test
	public void testSplitToArgumentsWithNothing() throws Exception {
		testClass = new CommandImpl("add test");
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] {};
		String[] actual = (String[]) splitToArguments.invoke(testClass,
				"add test");
		assertArrayEquals(
				"Testing splitToArguments for no additional arguments",
				expected, actual);
	}

	@Test
	public void testSplitToArgumentsWithOneWord() throws Exception {
		testClass = new CommandImpl("add hello --tag test");
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] { "tag test" };
		String[] actual = (String[]) splitToArguments
				.invoke(testClass, "add hello --tag test");
		assertArrayEquals("Testing splitToArguments with one word before dash",
				expected, actual);
	}

	@Test
	public void testSplitToArgumentsWithMultipleWords() throws Exception {
		testClass = new CommandImpl("add test multiple --tag important");
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] { "tag important" };
		String[] actual = (String[]) splitToArguments.invoke(testClass,
				"add test multiple --tag important");
		assertArrayEquals(
				"Testing splitToArguments with multiple words before dash",
				expected, actual);
	}

	@Test
	public void testSplitToArgumentsWithSingleDash() throws Exception {
		testClass = new CommandImpl("add test -a single");
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] { "a single" };
		String[] actual = (String[]) splitToArguments.invoke(testClass,
				"add test -a single");
		assertArrayEquals(
				"Testing splitToArguments with Single dash in string",
				expected, actual);
	}

	@Test
	public void testSplitToArgumentsWithTwoDashes() throws Exception {
		testClass = new CommandImpl("add test -a multiple dashes --tag test");
		Method splitToArguments = getMethodFromClass("splitToArguments",
				String.class);
		String[] expected = new String[] { "a multiple dashes", "tag test" };
		String[] actual = (String[]) splitToArguments.invoke(testClass,
				"add test -a multiple dashes --tag test");
		assertArrayEquals(
				"Testing splitToArguments with two sets of dashes in string",
				expected, actual);

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
