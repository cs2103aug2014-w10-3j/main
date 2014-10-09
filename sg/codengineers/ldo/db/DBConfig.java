package sg.codengineers.ldo.db;

/**
 * This model defines the database configurations of the
 * program, including which model class has access to which
 * type of database. It also helps to initialize all the 
 * database connections that the program has. This model is
 * special since it has direct connection to the database. 
 * This is done since initializing the database requires
 * information from the objects of this class, hence keeping
 * to the abstraction layers would inevitably result in a
 * circular definition.
 * 
 * @author Sean
 */

public class DBConfig {

}
