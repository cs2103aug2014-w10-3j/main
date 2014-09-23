package sg.codengineers.ldo.parser;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CommandImpl implements Command{
	public enum CommandType{
		create, retrieve, update, delete, show, invalid
	}
	
	private CommandType _commandType;
	private String _primaryOperand;
	private TreeMap<String, String> _argsMap;
	
	public CommandImpl(String commandWord, String primaryOperand){
		_commandType = getCommandType(commandWord);
		_primaryOperand = primaryOperand;
		_argsMap = new TreeMap<String, String>();
	}
	
	public CommandImpl.CommandType getCommandType() {
		return _commandType;
	}
	
	public String getPrimaryOperand() {
		return _primaryOperand;
	}
	
	public Iterator<Entry<String, String>> getArgsMapIterator() {
		return _argsMap.entrySet().iterator();
	}
}
