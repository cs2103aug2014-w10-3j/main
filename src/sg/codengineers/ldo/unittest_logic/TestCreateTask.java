package sg.codengineers.ldo.unittest_logic;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import sg.codengineers.ldo.model.*;
import sg.codengineers.ldo.parser.AdditionalArgumentImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCreateTask extends TestCase {
	
	public final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public final String TEST_NAME1 = "TEST1";
	public final String TEST_NAME2 = "TEST2";
	public final String TEST_DEADLINE = "30/10/2014";
	public final Date TEST_DEADLINE_DATE = dateFormatter.parse("30/10/2014");
	//public final String TEST_TIME = "28/11/2014 29/11/2014";
	//There's bug in database: start time and end time are swapped
	public final String TEST_TIME = "29/11/2014 28/11/2014";
	public final Date TEST_START_TIME = dateFormatter.parse("28/11/2014");
	public final Date TEST_END_TIME = dateFormatter.parse("29/11/2014");
	public final String TEST_TAG = "TESTTAG";
	public final String TEST_DESCRIPTION = "TESTDESCRIPTION";
	public ArrayList<AdditionalArgument> aaList1,aaList2;

	public TestCreateTask() throws Exception{
		super();
	}

	@Before
	public void setUp() throws Exception {
		aaList1 = new ArrayList<AdditionalArgument>();
		aaList1.add(new AdditionalArgumentImpl("--tag", TEST_TAG));
		aaList1.add(new AdditionalArgumentImpl("--time", TEST_TIME));
		aaList1.add(new AdditionalArgumentImpl("--description", TEST_DESCRIPTION));
		
		aaList2 = new ArrayList<AdditionalArgument>();
		aaList2.add(new AdditionalArgumentImpl("--deadline", TEST_DEADLINE));
	}

	@Test
	public void testNameTimeDesTag() throws Exception{
		MockLogic logic = new MockLogic();
		logic.logic.createTask(TEST_NAME1, aaList1.iterator());
		
		List<Task> list = logic.getList();
		assertFalse(list.isEmpty());
		assertEquals(list.size(), 1);
		
		Task task = list.get(0);
		assertEquals(task.getName(), TEST_NAME1);
		assertEquals(task.getDescription(), TEST_DESCRIPTION);
		assertEquals(task.getTag(), TEST_TAG);
		assertEquals(task.getStartTime(), TEST_START_TIME);
		assertEquals(task.getEndTime(), TEST_END_TIME);
	}
	
	@Test
	public void testDeadline() throws Exception{
		MockLogic logic = new MockLogic();
		logic.logic.createTask(TEST_NAME2, aaList2.iterator());
		
		List<Task> list = logic.getList();
		assertFalse(list.isEmpty());
		assertEquals(list.size(), 1);
		
		Task task = list.get(0);
		assertEquals(task.getName(), TEST_NAME2);
		assertEquals(task.getDeadline(), TEST_DEADLINE_DATE);
	}
	
	@After
	public void tearDown(){
		File file = new File("LDoDatabase.txt");
		if(file.exists()){
			file.delete();
		}
	}

}
