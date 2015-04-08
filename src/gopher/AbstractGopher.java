package gopher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * This class handles the basics of database connectivity by connecting, 
 * executing queries, and disconnecting from the database.
 * 
 * @author sedog
 */
public abstract class AbstractGopher {
    /** The name of the Java class to obtain a Class object for. */
    private final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
    /** The status code indicating a failure to locate the oracle driver. */
    public final int FAILURE = -1;
    /** The connection to use for interactions with the database. */
    private Connection connection;
    /** The statement to use for queries to the database. */
    private Statement statement;
	
    /**
     * Instantiates a new <code>AbstractGopher</code>.
     */
    protected AbstractGopher() {
        // Nothing to initialize
    }

    /**
     * This method attempts to establish a new connection to the database.
     */
    protected final void connect() {
        /* Verify the oracle database driver is installed. */
        try {
            Class.forName(DRIVER_CLASS);

        /* The oracle database driver was not found. */
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
            System.exit(FAILURE);
        }

        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.56.103:3306/courier", 
                    "team4","qwe123");
            System.out.println("Successfully connected to database");
        } catch (SQLException ex) {
            System.err.println("Unable to connect to the database.");
            ex.printStackTrace();
        }
    }

    /**
     * This method executes accepts a raw SQL statement and executes the 
     * underlying operation on the database.
     * 
     * @param	query	the <code>String</code> that contains the SQL query
     * @return	results	the <code>ResultSet</code> generated by the statement
     */
    protected List<Object> executeQuery(String query) {
        // The set of results returned from the database query; may be empty
        ResultSet results = null;
        // The list of processed results from the query; may be empty
        List<Object> output = null;


        try {
            connect();
            statement = connection.createStatement();
            if (query.contains("SELECT")) {
                results = statement.executeQuery(query);
                output = processResults(results);
                results.close();
            } else {
                statement.execute(query);
            }
            
            statement.close();
            disconnect();

        } catch (SQLTimeoutException ex) {
            System.err.println("Timeout exceeded for this database query.");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

        return output;
    }
    
    /**
     * Abstract method to be overridden by subclasses to handle entity-specific
     * data. 
     * 
     * @param results   <code>ResultSet</code> to parse for entity data
     * @return          <code>Object</code> populated with data from the record
     */
    protected abstract Object parseResult(ResultSet results);
    
    /**
     * Processes the results from a database query.
     * 
     * @param results   <code>ResultSet</code> to parse for entity data
     * @return          list of objects populated with results, if any exist
     */
    private List<Object> processResults(ResultSet results) {
        // The list of processed results
        List<Object> list = new LinkedList<>();
        
        try {
            while (results.next()) {
                list.add(parseResult(results));
            }
        } catch (SQLException ex) {
            // Nothing to do here
        }
        
        return list;
    }

    /**
     * This method attempts to gracefully disconnect from the database.
     */
    protected final void disconnect() {
        if (null != connection) {
            /* Attempt to gracefully disconnect. */
            try {
                connection.close();

            /* Some error occurred while disconnection. Print stacktrace. */
            } catch (SQLException ex) {
                System.err.println("Error encountered during disconnect.");
                ex.printStackTrace();
            }
        }
    }
}
