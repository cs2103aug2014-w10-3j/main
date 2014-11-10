//@author A0112171Y

package sg.codengineers.ldo.logic;

import java.io.IOException;
import java.sql.Time;

import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.parser.ResultImpl;

public class LogicStub extends Logic{
	public LogicStub(){
		super(true);
	}
	
	@Override
	public Result createTask(Command command) throws IOException{
		return new ResultImpl(CommandType.CREATE, "", new Time(System.currentTimeMillis()));
	}
	
	@Override
	public Result deleteTask(Command command) throws IOException{
		return new ResultImpl(CommandType.DELETE, "", new Time(System.currentTimeMillis()));
	}

	@Override
	public Result updateTask(Command command) throws IOException{
		return new ResultImpl(CommandType.UPDATE, "", new Time(System.currentTimeMillis()));
	}
	
	@Override
	public Result searchTask(Command command){
		return new ResultImpl(CommandType.SEARCH, "", new Time(System.currentTimeMillis()));
	}
	
	@Override
	public Result showTask(Command command) {
		return new ResultImpl(CommandType.RETRIEVE, "", new Time(System.currentTimeMillis()));
	}
	
	@Override
	public Result undoTask() {
		return new ResultImpl(CommandType.UNDO, "", new Time(System.currentTimeMillis()));
	}
	
	@Override
	public Result showHelp(Command command) {
		return new ResultImpl(CommandType.HELP, "", new Time(System.currentTimeMillis()));
	}
}
