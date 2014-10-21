package sg.codengineers.ldo.db;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class DatabaseTest {
	private static Database db;
	
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	private static final int THIRD = 2;
	
	@Before
	public void initDatabases() {
		db = Database.initDatabase();
		db.clear("Test");
	}
	
	@After
	public void clearDatabases() {
		db.clear("Test");
	}

	@Test
	public void testCreate() {
		boolean result = db.create("This is a test message", "Test");
		assertTrue(result);
		
		List<String> entries = db.read("Test");
		assertEquals("The entry is not the same as what is read",
				"This is a test message",
				entries.get(FIRST));
	}
	
	public void testUpdate() {
		
	}
	
	public void testRead() {
		
	}
	
	public void testDelete() {
		
	}

}
