//@author A0111163Y

package sg.codengineers.ldo.db;

/**
 * This class is the implementation of the DBConnector
 * interface. Specifically, it is the connection between
 * the program and a textfile representation of the data
 * in the program.
 */

import java.io.*;
import java.util.*;

public class TextDBConnector implements DBConnector {
	private static final String SEPARATOR = "<;>";
	private static final int FIRST_WORD = 0;

	private String filename;
	private File file;
	private List<String> dataList;

	/**
	 * Constructor
	 * 
	 * @param filename The name of the file to save the data to
	 * @throws IOException
	 */
	public TextDBConnector(String filename) {
		this.filename = filename;
		if (initFile()) {
			dataList = read();
		}		
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
	public boolean create(Object data) {
		return write(data.toString());
	}

	/**
	 * This method writes one data to the database
	 * 
	 * @param data The data to be written
	 * @return true if the operation succeeds
	 */
	private boolean write(String data) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			
			// Defensive code
			if (dataList == null || dataList.isEmpty()) {
				dataList = read();
			}

			bw.write(data);
			
			// A new line represents the separation of data
			bw.newLine();
			bw.close();
			
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
	public boolean update(Object data) {
		int id = Integer.parseInt(data
				.toString()
				.trim()
				.split(SEPARATOR)[FIRST_WORD]);
		dataList.remove(id);
		dataList.add(id, data.toString());
		return writeList();
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
			// Defensive check
			if (dataList == null || dataList.isEmpty()) {
				dataList = new ArrayList<String>();
				
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;

				while ((line = br.readLine()) != null) {
					dataList.add(line);
				}
		
				br.close();
			}
			
			return dataList;
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		}
	}

	@Override
	public boolean delete(Object data) {
		return update(data.toString() + SEPARATOR + "deleted");
	}

	@Override
	public boolean clear() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			
			bw.write("");			
			bw.close();
			dataList.clear();

			return true;
		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return false;
		}
	}
}
