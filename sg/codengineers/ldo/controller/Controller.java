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
	// parser
	Command command;
	// logic
	Logic logic;
	// ui
	Input input;
	Output output;

	public Controller() {
		logic = new Logic();
		input = new InputImpl();
		output = new OutputImpl();
	}

	public void run() {
		try {
			while (true) {
				String userInput = input.readFromUser();
				command = new CommandImpl(userInput);
				Result result = executeCommand();
				output.displayResult(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			output.displayError(e.getMessage(), e);
		}
	}

	public Result executeCommand() throws Exception {
		CommandType commandType = command.getCommandType();
		switch (commandType) {
		case CREATE:
			return logic.createTask(command.getPrimaryOperand(),
					command.getIterator());
		case DELETE:
			return logic.deleteTask(command.getPrimaryOperand(),
					command.getIterator());
		case UPDATE:
			return logic.updateTask(command.getPrimaryOperand(),
					command.getIterator());
		case RETRIEVE:
			return logic.retrieveTask(command.getPrimaryOperand(),
					command.getIterator());
		case SHOW:
			return logic.showTasks(command.getIterator());
		default:
			throw new Exception("Invalid command.");
		}
	}
}
