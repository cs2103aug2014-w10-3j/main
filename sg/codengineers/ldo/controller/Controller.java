package sg.codengineers.ldo.controller;

import sg.codengineers.ldo.logic.Logic;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Input;
import sg.codengineers.ldo.model.Output;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.CommandImpl;
import sg.codengineers.ldo.ui.InputImpl;
import sg.codengineers.ldo.ui.OutputImpl;

public class Controller {
	// logic
	private static Logic logic;
	// ui
	private Input input;
	private Output output;

	public Controller(){
		logic = Logic.getInstance();
		input = new InputImpl();
		output = new OutputImpl();
	}
	
	public static Controller getControllerInstance(){
		return new Controller();
	}

	public void run() {
		try {
			Command welcomeCommand, command;
			Result result;
			
			welcomeCommand = new CommandImpl("show welcome");
			result = executeCommand(welcomeCommand);
			output.displayResult(result);
			
			while (true) {
				try{
					String userInput = input.readFromUser();
					command = new CommandImpl(userInput);
					result = executeCommand(command);
					output.displayResult(result);
				} catch (Exception e) {
					output.displayException(e);
				}
			}
		} catch (Exception e) {
			output.displayException(e);
		}
	}

	public Result executeCommand(Command command) throws Exception {
		CommandType commandType = command.getCommandType();
		switch (commandType) {
			case CREATE:
				return logic.createTask(command.getPrimaryOperand(),
						command.getAdditionalArguments());
			case DELETE:
				return logic.deleteTask(command.getPrimaryOperand(),
						command.getAdditionalArguments());
			case UPDATE:
				return logic.updateTask(command.getPrimaryOperand(),
						command.getAdditionalArguments());
			case RETRIEVE:
				return logic.retrieveTask(command.getPrimaryOperand(),
						command.getAdditionalArguments());
			default:
				throw new Exception("Invalid command.");
		}
	}
}
