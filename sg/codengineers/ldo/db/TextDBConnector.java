package sg.codengineers.ldo.db;

/**
 * This class is the implementation of the DBConnector
 * interface. Specifically, it is the connection between
 * the program and a textfile representation of the data
 * in the program.
 * 
 * @author Sean
 */

import java.io.*;
import java.util.*;

public class TextDBConnector implements DBConnector {

	private String filename;
	private File file;
	private List<String> dataList;

	/**
	 * Constructor
	 * @param filename The name of the file to save the data to
	 */
	public TextDBConnector(String filename) {
		this.filename = filename;
		initFile();
		dataList = read();
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

	@Override
	public void create(String data) {
		write(data);
	}
	
}
