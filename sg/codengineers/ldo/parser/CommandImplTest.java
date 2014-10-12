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
		Method getFirstWord = getMethodFromClass("getFirstWord", String.class);
		assertEquals("Getting first word from string", "first",
				getFirstWord.invoke(testClass, "first second third"));
	}

	@Test
	public void testRemoveFirstWord() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method removeFirstWord = getMethodFromClass("removeFirstWord",
				String.class);
		assertEquals("Removing first word from string", "second third",
				removeFirstWord.invoke(testClass, "first second third"));
	}

	@Test
	public void testGetParameterWithNothing() throws NoSuchMethodException,
			NullPointerException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
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
	public void testGetParameterWithOneWord() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
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
	public void testGetParameterWithMultipleWords()
			throws NoSuchMethodException, NullPointerException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
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
	public void testGetParametersWithSingleDash() throws NoSuchMethodException,
			NullPointerException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
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
	public void testGetParametersWithTwoDashes() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, NullPointerException, SecurityException {
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
