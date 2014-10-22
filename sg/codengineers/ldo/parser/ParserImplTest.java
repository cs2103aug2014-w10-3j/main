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

	@Test(expected = Exception.class)
	public void testAddWithEmptyPrimary() throws Exception {
		parserTest.parse("add ");
	}

	@Test(expected = Exception.class)
	public void testUpdateWithEmptyPrimary() throws Exception {
		parserTest.parse("update ");
	}

	@Test(expected = Exception.class)
	public void testDeleteWithEmptyPrimary() throws Exception {
		parserTest.parse("delete ");
	}

	@Test(expected = Exception.class)
	public void testSearchWithEmptyPrimary() throws Exception {
		parserTest.parse("search ");
	}

	@Test(expected = Exception.class)
	public void testUpdateWithNonDigit() throws Exception {
		parserTest.parse("update a");
	}

	@Test(expected = Exception.class)
	public void testDeleteWithNonDigit() throws Exception {
		parserTest.parse("delete a");
	}
}
