package sg.codengineers.ldo.unittest_logic;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import sg.codengineers.ldo.logic.Logic;
import sg.codengineers.ldo.model.Task;

public class MockLogic {
	Field taskListField;
	Logic logic;
	
	public MockLogic() throws Exception{
		File file = new File("LDoDatabase.txt");
		if(file.exists()){
			file.delete();
		}
		logic = Logic.getLogic();
		reflect();
	}
	
	private void reflect() throws Exception{
		Class<?> logicClass = logic.getClass();
		taskListField= logicClass.getDeclaredField("_taskList");
		taskListField.setAccessible(true);
	}
	
	@SuppressWarnings({"unchecked" })
	public List<Task> getList() throws Exception{
		return (List<Task>)taskListField.get(logic);
	}
	
}
