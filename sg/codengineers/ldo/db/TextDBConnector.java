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

	@Override
	public void update(String data) {
		
	}

	public void write(String data) {
		try {
	
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(data);
			
			// A new line represents the separation of data
			bw.newLine();
			bw.close();
			
			// Defensive code
			if (dataList == null || dataList.isEmpty()) {
				dataList = read();
			}
			
			// The dataList would exist by now
			dataList.add(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> read(){
		try {			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			
			// Defensive check
			if (dataList == null) {
				dataList = new ArrayList<String>();
			}
			
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}

			br.close();
			return dataList;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void delete(int id) {

	}
}
