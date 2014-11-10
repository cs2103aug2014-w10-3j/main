package sg.codengineers.ldo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import sg.codengineers.ldo.logic.Logic;
import sg.codengineers.ldo.logic.LogicStub;
import sg.codengineers.ldo.model.Command;
import sg.codengineers.ldo.model.Parser;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.Command.CommandType;
import sg.codengineers.ldo.model.UI;
import sg.codengineers.ldo.parser.ParserImpl;
import sg.codengineers.ldo.ui.UIImpl;

/**
 * Controller controls the execution of L'Do.
 * It reads the user's input which contains a command,
 * routes what to do with the data according to the
 * command type, and prints the corresponding feedback.
 * 
 * Uses java.util.Date class which has been deprecated.
 */
@SuppressWarnings("deprecation")
public class Controller {
	//@author A0112171Y
	
	//Logger instance
	private final static Logger logger = Logger.getLogger(Controller.class.getName()); 
	
	//These are command strings and messages required in Controller class
	private static String COMMAND_SHOW_TODAY = "search --time %s";
	private static String MSG_ERROR_UNABLE_TO_START_LDO = "Sorry! There is an error when starting the program.\n"
														+ "Please restart the program.";
	private static String MSG_ERROR_UNABLE_TO_EXECUTE_CMD = "Sorry! There is an error within the program.\n"
														+ "Please re-enter the command or restart the program.";
	private static String MSG_GCAL_AUTH_URL = "Open this URL from your web browser to login to Google Calendar:\n%s";
	
	//Logging directory and file name
	private static String LOG_DIRECTORY = "log/";
	private static String LOG_FILE_NAME = "log.log";
	
	// Logic instance
	private static Logic logic;

	// UI instances
	private UI ui;
	
	// Parser instance
	private Parser parser;
	
	/**
	 * Constructors
	 */
	private Controller(){
		logic = Logic.getInstance();
		ui = new UIImpl();
		parser = new ParserImpl();
		
		activateLogger();
	}
	
	private void activateLogger(){
		try {
			new File(LOG_DIRECTORY).mkdirs();
			Handler fileHandler = new FileHandler(LOG_DIRECTORY + LOG_FILE_NAME);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
			logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * This constructor allows the controller to use a logic stub
	 * and has an access modifier of package access, because it is
	 * used only for unit testing purposes within the package.
	 * @param stub
	 * 			true if the unit is under testing
	 */
	Controller(boolean stub){
		assert (stub == true);
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
		logger.entering("Controller", "run");
		logger.log(Level.INFO, "Running L'Do");
		
		processWelcome();
		
		while (true) {
			String userInput = ui.readFromUser();
			processCommand(userInput);
		}
	}
	
	/**
	 * Processes raw command string and displays feedback
	 * to continue interaction with user.
	 * 
	 * @param rawCommand
	 * 			unprocessed command string
	 */
	public void processCommand(String rawCommand){
		logger.entering("Controller", "processCommand", rawCommand);
		logger.log(Level.INFO, "Process raw command string: {0}", rawCommand);
		
		try {
			Command command;
			Result result;
			CommandType commandType;
			
			command = parser.parse(rawCommand);
			commandType = command.getCommandType();
			
			if (isValidCommandType(commandType)){
				result = executeCommand(command);
				commandType = result.getCommandType();
				
				if (isValidCommandType(commandType)){
					ui.displayResult(result);
				} else {
					ui.displayError(result.getMessage());
				}
			} else {
				ui.displayError(command.getMessage());
			}
		} catch (Exception e) {
			ui.displayError(MSG_ERROR_UNABLE_TO_EXECUTE_CMD);
		}
	}
	
	/**
	 * Displays welcome message to start interaction with user.
	 */
	public void processWelcome() {
		logger.entering("Controller", "processWelcome");
		logger.log(Level.INFO, "Process welcome message");
		
		try {
			//Create new date
			Date date = new Date();
			StringBuilder dateString = new StringBuilder();
			dateString.append(date.getDate() + "/");
			dateString.append(date.getMonth()+1 + "/");
			dateString.append(date.getYear()+1990);
			
			Command command = parser.parse(String.format(COMMAND_SHOW_TODAY,dateString.toString()));
			Result result = executeCommand(command);
			ui.displayWelcome(result);
		} catch (Exception e) {
			ui.displayError(MSG_ERROR_UNABLE_TO_START_LDO);
		}
	}
	
	/**
	 * Validates whether the command type is a valid command type.
	 * Used to check the command type in Command and Result classes.
	 * 
	 * @param commandType
	 * @return true if valid command type, false otherwise
	 */
	private boolean isValidCommandType(CommandType commandType){
		switch (commandType){
			case CREATE:
			case DELETE:
			case UPDATE:
			case RETRIEVE:
			case SEARCH:
			case HELP:
			case UNDO:
			case EXIT:
			case SYNC:
				return true;
			case INVALID:
			default:
				return false;
		}
	}
	
	/**
	 * Shows exit message and exits the system
	 */
	private void terminate(){
		logger.entering("Controller", "terminate");
		logger.log(Level.INFO, "Terminating L'Do");
		
		ui.displayExit();
		System.exit(0);
	}
	
	private Result GCal(){
		logic.gCalAuth();
		ui.displayMessage(String.format(MSG_GCAL_AUTH_URL));
		
		String userGCalAuthKey = ui.readFromUser();
		return logic.syncGCal(userGCalAuthKey);
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
		
		switch (commandType) {
			case CREATE:
				return logic.createTask(command);
			case DELETE:
				return logic.deleteTask(command);
			case UPDATE:
				return logic.updateTask(command);
			case RETRIEVE:
				return logic.showTask(command);
			case SEARCH:
				return logic.searchTask(command);
			case HELP:
				return logic.showHelp(command);
			case UNDO:
				return logic.undoTask();
			case EXIT:
				terminate();
			case SYNC:
				return GCal();
			case INVALID:
			default:
				throw new Error("Invalid command type.");
		}
	}
}
