package sg.codengineers.ldo.parser;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This interface specifies the available public methods to the Result Class
 * 
 * @author Victor Hazali
 * 
 */
public interface Result {

	public CommandType getCommandType();

	public String getOperationTime();

	public Iterator<ArrayList<Task>> getTasksIterator();
}
