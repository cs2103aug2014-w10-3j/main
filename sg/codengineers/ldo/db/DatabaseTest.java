package sg.codengineers.ldo.db;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class DatabaseTest {
	private static Database db;
	
	private static final int FIRST = 0;
	
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

	}
	
	public void testUpdate() {
		
	}
	
	public void testRead() {
		
	}
	
	public void testDelete() {
		
	}

}
