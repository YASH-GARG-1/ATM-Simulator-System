
package atm.simulator.system;

import java.sql.*;

public class Conn {
    
    Connection c; // for connection.
    Statement s; // for taking the mysql statement;
    
    
    // JDBC has 5 steps:
    // 1. Register the driver
    // 2. Create connection
    // 3. Create Statement
    // 4. Execute the Query.
    // 5. Close Connection
    
    public Conn()
    {        
        // we used exception handelling here because we may get errors from sql in the run time.
        try
        {
            // For connecting the sql to java
            
            // Step 1: Register the driver.
            // Class.forName(com.mysql.cj.jdbc.Driver);
            // There is no need to register the driver once we have impotrted the mysql connector jar file.
            
            
            // Step 2: Create the connection;
            c = DriverManager.getConnection("jdbc:mysql:///atmsimulatorsystem","root","wordpass");
            // here we are establishing a connection with the mysql server using
            // jdbc connectivity. Our mysql works on local MAchine with port number 3306;
            // so there is no need to write localhost:3306 in the getConnection statement.
            
            s = c.createStatement();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
