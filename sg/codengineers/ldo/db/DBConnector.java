package sg.codengineers.ldo.db;

import java.util.ArrayList;
import java.io.*;

import sg.codengineers.ldo.logic.TaskImpl;
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
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		ArrayList<Task> taskLst = new ArrayList<>();
		while ((line =br.readLine()) != null) {
			taskLst.add(parseLine(line));
		}
	}

	}
	
}
