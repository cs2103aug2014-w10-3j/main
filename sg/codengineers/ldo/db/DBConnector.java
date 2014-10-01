package sg.codengineers.ldo.db;

import java.util.ArrayList;

import sg.codengineers.ldo.model.Task;

public class DBConnector {

	private String filename;
	private File file;

	public DBConnector(String filename) {
		this.filename = filename;
		file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public ArrayList<Task> retrieveTaskList(){
		return null;
	}
	
}
