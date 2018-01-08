package Archery_trainer.server;

import java.sql.*;

public class DatabaseConnection {

    private final String urlDatabaseServer = "archerytrainerdb.cl9ugyt9v9y1.us-east-1.rds.amazonaws.com";
    private String url;
    private String databaseName;
    private String username;
    private String password;

    public DatabaseConnection(String _databaseName, String _username, String _password) {
        databaseName = _databaseName;
        username = _username;
        password = _password;
        url = "jdbc:mysql://" + urlDatabaseServer + "/" + databaseName;
    }

    //Return the highest archer id in database
    public int getMaxIndexArcher() {
        String query = "SELECT MAX(Id) FROM " + Archer.getTableName() + ";";


        try (Connection conn = DriverManager.getConnection(url, username, password)){

            Statement ps = conn.createStatement();
            ResultSet res = ps.executeQuery(query);

            if(res.next()) {
                return res.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Bad response");
            e.printStackTrace();
        }

        return -1;

    }


    public void insertArcher(Archer a) {

        String query = "INSERT INTO " + Archer.getTableName() +
                " (Fname, Lname, Weight, Height, RightHanded) VALUES (?, ?, ?, ?, ?)";

        //Create connection
        try (Connection conn = DriverManager.getConnection(url, username, password)){

            //Create statement and set the archer's values
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, a.getFirstName());
            pstmt.setString(2, a.getLastName());
            pstmt.setInt(3, a.getWeight());
            pstmt.setInt(4, a.getHeight());
            pstmt.setBoolean(5, a.isRightHanded());

            pstmt.execute();

        } catch (SQLException e) {
            System.out.println("Bad response");
            e.printStackTrace();
        }

    }

}
