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
	
}
