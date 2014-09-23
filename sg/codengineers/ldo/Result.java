package sg.codengineers.ldo;

import java.util.ArrayList;
import java.util.Iterator;

public interface Result {

	public CommandType getCommandType();
    public String getOperationTime();
    public Iterator<ArrayList<Task>> getTasksIterator();
}
