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
	 * 
	 * @param filename The name of the file to save the data to
	 * @throws IOException
	 */
	public TextDBConnector(String filename) throws IOException {
		this.filename = filename;
		initFile();
		dataList = read();
	}

	/**
	 * Initializes the file to make sure it exist before any IO
	 * operations are performed on it. Create a new file with the
	 * supplied filename if the file does not already exist
	 * 
	 * @return true if the operation succeeds
	 */
	private boolean initFile() {
		try {
			file = new File(filename);

			if (!file.exists()) {
				file.createNewFile();
			}
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		}
	}

	@Override
	public void create(String data) throws IOException {
		write(data);

	/**
	 * This method writes one data to the database
	 * 
	 * @param data The data to be written
	 * @return true if the operation succeeds
	 */
	public boolean write(String data) {
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
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		}
	}

	@Override
	public void update(String data) throws IOException {
		int id = Integer.parseInt(data.trim().split(";")[0]);
		dataList.remove(id);
		dataList.add(id, data);
		writeList();
	}

	/**
	 * This method rewrites the database with the data in the list
	 * 
	 * @return true if the operation succeeds
	 */
	private boolean writeList() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			for (String s : dataList) {
				bw.write(s);
				bw.newLine();
			}
			
			bw.close();
			return true;
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		}
	}

	@Override
	public List<String> read() {
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
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	@Override
	public void delete(String data) throws IOException {
		update(data + ";deleted");
	}
}
