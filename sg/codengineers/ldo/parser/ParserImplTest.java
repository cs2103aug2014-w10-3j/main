package sg.codengineers.ldo.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sg.codengineers.ldo.model.Parser;

public class ParserImplTest {

	private Parser	parserTest	= new ParserImpl();

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = Exception.class)
	public void testNoInput() throws Exception {
		parserTest.parse("");
	}

	@Test(expected = Exception.class)
	public void testBlankInput() throws Exception {
		parserTest.parse(" ");
	}

	@Test(expected = Exception.class)
	public void testMultipleBlanksInput() throws Exception {
		parserTest.parse("          ");
	}
}
