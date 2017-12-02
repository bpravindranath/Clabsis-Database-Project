package sample;

import java.sql.*;
import java.util.*;

public class DbConnection {

//    final String username = "csmith131";
//    final String password = "Cosc*ndda";
//    final String myUrl = "jdbc:mysql://triton.towson.edu:3360/csmith131db";

    public Connection Connect() throws ClassNotFoundException, SQLException {
        try {
            String username = "csmith131";

            String password = "Cosc*ndda";

            String myUrl = "jdbc:mysql://triton.towson.edu:3360/csmith131db";

            String myDriver = "com.mysql.jdbc.Driver";

            Class.forName(myDriver);

            Connection conn = DriverManager.getConnection(myUrl, username, password);


           return conn;

        }catch(Exception e){

            System.err.println("Got an exception!");
            System.err.println(e.getMessage());

        }

    return null;
    }
}
