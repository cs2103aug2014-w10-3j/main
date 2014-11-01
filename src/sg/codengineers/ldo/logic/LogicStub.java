package sg.codengineers.ldo.logic;

import java.io.IOException;
import java.sql.Time;
import java.util.Iterator;

import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.parser.ResultImpl;

public class LogicStub extends Logic{
	public LogicStub(){
		super(true);
	}
	
	public Result createTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) throws IOException{
		return new ResultImpl(CommandType.CREATE, "", new Time(System.currentTimeMillis()));
	}

	public Result deleteTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) throws IOException{
		return new ResultImpl(CommandType.DELETE, "", new Time(System.currentTimeMillis()));
	}

	public Result updateTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) throws IOException{
		return new ResultImpl(CommandType.UPDATE, "", new Time(System.currentTimeMillis()));
	}

	public Result retrieveTask(String primaryOperand,
			Iterator<AdditionalArgument> iterator) {
		return new ResultImpl(CommandType.RETRIEVE, "", new Time(System.currentTimeMillis()));
	}
}
