package com.soward.db;

/**
 * @author Scott Soward
 *
 */

import java.sql.*;
import java.sql.Connection;

public class DB {
    // Load the database driver once
    static {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException cnfe ) {
            cnfe.printStackTrace();
        }
    }

    // Creates connections to the database.

    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection( "jdbc:mysql://localhost:3306/hometeaching2", "root", "root" );
    }

}
