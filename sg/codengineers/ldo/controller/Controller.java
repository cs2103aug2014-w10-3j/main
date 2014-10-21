package sg.codengineers.ldo.controller;

import java.util.Iterator;

import sg.codengineers.ldo.logic.Logic;
import sg.codengineers.ldo.logic.LogicStub;
import sg.codengineers.ldo.model.AdditionalArgument;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Input;
import sg.codengineers.ldo.model.Output;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.parser.CommandImpl;
import sg.codengineers.ldo.ui.InputImpl;
import sg.codengineers.ldo.ui.OutputImpl;

/**
 * Controller controls the execution of L'Do.
 * It reads the user's input which contains a command,
 * routes what to do with the data according to the
 * command type, and prints the corresponding feedback.
 */
public class Controller {
	//@author A0112171Y
	
	// Logic instance
	private static Logic logic;
	
	// UI instances
	private Input input;
	private Output output;
	
	/**
	 * Constructors
	 */
	public Controller(){
		logic = Logic.getInstance();
		input = new InputImpl();
		output = new OutputImpl();
	}
	
	protected Controller(boolean stub){
		logic = new LogicStub();
	}
	
	/**
	 * Controller is a singleton.
	 * This function generates one controller instance.
	 * 
	 * @return one controller instance
	 */
	public static Controller getInstance(){
		return new Controller();
	}
	
	/**
	 * This function controls the execution of L'Do,
	 * from reading the user's input, passing it to the
	 * appropriate functions, and printing the feedback
	 * to the user.
	 */
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
	
	/**
	 * This function is the control flow of data in L'Do.
	 * It passes data to be processed by the Logic component
	 * according to the command type.
	 * 
	 * @param command contains the command type and data to be processed
	 * @return result contains the information needed for feedback
	 * @throws Exception
	 */
	Result executeCommand(Command command) throws Exception {
		CommandType commandType = command.getCommandType();
		String primaryOperand = command.getPrimaryOperand();
		Iterator<AdditionalArgument> iterator = command.getAdditionalArguments();
		
		switch (commandType) {
			case CREATE:
				return logic.createTask(primaryOperand, iterator);
			case DELETE:
				return logic.deleteTask(primaryOperand, iterator);
			case UPDATE:
				return logic.updateTask(primaryOperand, iterator);
			case RETRIEVE:
				return logic.retrieveTask(primaryOperand, iterator);
			default:
				throw new Exception("Invalid command.");
		}
	}
}
