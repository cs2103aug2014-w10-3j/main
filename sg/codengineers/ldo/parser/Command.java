package sg.codengineers.ldo.parser;

import java.util.Iterator;
import java.util.Map.Entry;

public interface Command {
	public CommandImpl.CommandType getCommandType();
	public String getPrimaryOperand();
	public Iterator<Entry<String, String>> getArgsMapIterator();
}
