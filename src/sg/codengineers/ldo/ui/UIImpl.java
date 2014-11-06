package sg.codengineers.ldo.ui;

import sg.codengineers.ldo.model.Input;
import sg.codengineers.ldo.model.Output;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.UI;

public class UIImpl implements UI {

	private Input	_input	= new InputImpl();
	private Output	_output	= new OutputImpl();

	@Override
	public String readFromUser() {
		return _input.readFromUser();
	}

	@Override
	public void displayResult(Result result) {
		_output.displayResult(result);
	}

	@Override
	public void displayError(String message) {
		System.out.println(message);
	}

	@Override
	public void displayWelcome(Result result) {
		_output.displayWelcome(result);
	}

	@Override
	public void displayExit() {
		_output.displayExit();
	}
	
	@Override
	public void displayMessage(String message){
		_output.displayMessage(message);
	}
}
