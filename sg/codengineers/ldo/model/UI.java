package sg.codengineers.ldo.model;

/**
 * This interface specifies all the available methods for the UI component
 * 
 * @author Victor Hazali
 * 
 */
public interface UI {

	public String readFromUser();

	public void showToUser(Result result) throws Exception;

	public void showToUser(String message);

	public void showWelcomeMessage();
}
