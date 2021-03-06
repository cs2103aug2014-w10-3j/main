//@author A0111163Y

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
		// Create a new entry in the database
		boolean result = db.create("This is a test message", "Test");
		assertTrue(result);
		
		// Make sure it is there
		List<String> entries = db.read("Test");
		assertEquals("The entry is not the same as what is read",
				"This is a test message",
				entries.get(FIRST));
	}
	
	@Test
	public void testUpdate() {
		// Add a few messages in to test and make sure they are created
		boolean result = db.create("0<;>This is a test message", "Test");
		assertTrue(result);
		result = db.create("1<;>This is another test message", "Test");
		assertTrue(result);
		result = db.create("2<;>This is the last test message", "Test");
		assertTrue(result);
		
		// Update one of them
		result = db.update("2<;>This is an updated message", "Test");
		assertTrue(result);
		
		// Make sure that it is changed
		List<String> entries = db.read("Test");
		assertEquals("The message was not updated",
				"2<;>This is an updated message",
				entries.get(THIRD));
	}
	
	@Test
	public void testRead() {
		// Add a few messages in to test and make sure they are created
		boolean result = db.create("This is a test message", "Test");
		assertTrue(result);
		result = db.create("This is another test message", "Test");
		assertTrue(result);
		result = db.create("This is the last test message", "Test");
		assertTrue(result);
		
		// Read them and ensure that they are correct
		List<String> entries = db.read("Test");
		assertEquals("The first entry is not the same as what is read",
				"This is a test message",
				entries.get(FIRST));
		assertEquals("The second entry is not the same as what is read",
				"This is another test message",
				entries.get(SECOND));
		assertEquals("The third entry is not the same as what is read",
				"This is the last test message",
				entries.get(THIRD));
		
		// Clear the database and ensure nothing is read
		assertTrue(db.clear("Test"));
		assertTrue("The list returned should be empty", db.read("Test").isEmpty());
	}
	
	@Test
	public void testDelete() {
		// Add a few messages in to test and make sure they are created
		boolean result = db.create("0<;>This is a test message", "Test");
		assertTrue(result);
		result = db.create("1<;>This is another test message", "Test");
		assertTrue(result);
		result = db.create("2<;>This is the last test message", "Test");
		assertTrue(result);
		
		// Delete a message
		result = db.delete("2<;>This is the last test message", "Test");
		assertTrue(result);
		
		// Ensure that the entry is soft deleted
		List<String> entries = db.read("Test");
		assertEquals("The message was not deleted",
				"2<;>This is the last test message<;>deleted",
				entries.get(THIRD));
	}
	
	@Test
	public void testClear() {
		// Add a few messages in to test and make sure they are created
		boolean result = db.create("0<;>This is a test message", "Test");
		assertTrue(result);
		result = db.create("1<;>This is another test message", "Test");
		assertTrue(result);
		result = db.create("2<;>This is the last test message", "Test");
		assertTrue(result);
		
		// Clear the database
		result = db.clear("Test");
		assertTrue(result);

		// Ensure that the database is cleared
		List<String> entries = db.read("Test");
		assertTrue("The list returned should be empty", db.read("Test").isEmpty());
	}
}
