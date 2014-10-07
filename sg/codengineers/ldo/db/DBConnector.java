package sg.codengineers.ldo.db;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.io.*;

import sg.codengineers.ldo.logic.TaskImpl;
import sg.codengineers.ldo.model.Task;

public class DBConnector {

	private String filename;
	private File file;

	/**
	 * Constructor
	 * @param filename The name of the file to save the data to
	 */
	public DBConnector(String filename) {
		this.filename = filename;
		initFile();
	}

	/**
	 * Initializes the file to make sure it exist before any IO
	 * operations are performed on it. Create a new file with the
	 * supplied filename if the file does not already exist
	 */
	private void initFile() {
		try {
			file = new File(filename);

			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Task> read(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			List<Task> taskLst = new ArrayList<Task>();
			while ((line = br.readLine()) != null) {
				taskLst.add(parseLine(line));
			}
			br.close();
			return taskLst;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Task parseLine(String line) {
		String[] lineArr = line.trim().split(" ");
		Task t = new TaskImpl(Integer.parseInt(lineArr[0]), lineArr[1]);
		t.setDescription(lineArr[2]);
		//t.setTimeStart(new Date(lineArr[3]));
		return t;
	}

	public void write(Task task) {
		try {
			String line = task.getId() + " ";
			line += line + task.getName() + " ";
			line += line + task.getDescription() + " ";
			line += line + task.getStartTime() + " ";
			line += line + task.getEndTime() + " ";
			line += line + task.getTag();

			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(line);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
