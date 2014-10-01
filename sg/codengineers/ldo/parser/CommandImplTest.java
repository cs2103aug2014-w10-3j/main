package sg.codengineers.ldo.parser;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.codengineers.ldo.model.Command;

public class CommandImplTest {

	private static Command	testClass;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testConstructor() {
		testClass=new CommandImpl("add test");
		fail("Not yet implemented");
	}

}
