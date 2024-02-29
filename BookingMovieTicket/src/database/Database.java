package database;

import java.sql.*;

public class Database {

    private Connection connect = null;
    private static Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    public static Connection connectDb (){
        try {
            Class.forName("org.postgresql.Driver");
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cinema", "postgres", "0000");

            if(connect != null){
                System.out.println("Connection Established");
            }
            else{
                System.out.println("Connection Failed");
            }

            return connect;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
