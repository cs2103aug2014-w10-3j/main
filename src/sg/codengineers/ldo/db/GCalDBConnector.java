package sg.codengineers.ldo.db;

/**
 * This class is the implementation of the DBConnector
 * interface. Specifically, it is the connection between
 * the program and google calendar. It handles the synchronization 
 * of data between the program and google calendar.
 * 
 * @author Sean
 */

import java.util.List;

public class GCalDBConnector implements DBConnector {

	@Override
	public boolean create(String data) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean update(String data) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<String> read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String data) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean clear() {
		// TODO Auto-generated method stub
		return false;
	}

}
