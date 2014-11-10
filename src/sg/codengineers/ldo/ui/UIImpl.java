package sg.codengineers.ldo.ui;

import org.fusesource.jansi.AnsiConsole;

import sg.codengineers.ldo.model.Input;
import sg.codengineers.ldo.model.Output;
import sg.codengineers.ldo.model.Result;
import sg.codengineers.ldo.model.UI;

//@author A0110741X
public class UIImpl implements UI {

	private Input	_input	= new InputImpl();
	private Output	_output	= new OutputImpl();

	public UIImpl() {
		AnsiConsole.systemInstall();
	}

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
		_output.displayError(message);
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
	public void displayMessage(String message) {
		_output.displayMessage(message);
	}
}
