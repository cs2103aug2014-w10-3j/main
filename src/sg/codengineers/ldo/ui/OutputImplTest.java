//@author A0110741X

package sg.codengineers.ldo.ui;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.codengineers.ldo.logic.TaskImpl;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Output;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Task;
import sg.codengineers.ldo.parser.ResultImpl;

public class OutputImplTest {

	private final ByteArrayOutputStream	outContent	= new ByteArrayOutputStream();

	private Result						_result;
	private List<Task>					_taskList;
	private Output						_testClass;

	@Before
	public void setUp() throws Exception {
		_taskList = new ArrayList<Task>();
		System.setOut(new PrintStream(outContent));
		_testClass = new OutputImpl();
		outContent.reset();
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	}

	@Test
	public void testOutputForAdd() {
		_taskList.add(new TaskImpl("test"));
		_result = new ResultImpl(CommandType.CREATE, "test", null,
				_taskList);
		_testClass.displayResult(_result);
		assertEquals("Added test\nName: test\n", outContent.toString());
	}

	@Test
	public void testOutputforUpdate() {
		Task temp = new TaskImpl("test");
		temp.setDescription("tester");
		_taskList.add(temp);
		_result = new ResultImpl(CommandType.UPDATE, "1", null, _taskList);
		_testClass.displayResult(_result);
		assertEquals("Updated test\nName: test\nDescription: tester\n",
				outContent.toString());
	}

	@Test
	public void testOutputDelete() {
		_taskList.add(new TaskImpl("test"));
		_result = new ResultImpl(CommandType.DELETE, "test", null, _taskList);
		_testClass.displayResult(_result);
		assertEquals("Deleted test\n", outContent.toString());
	}

}
