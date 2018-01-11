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

            conn.close();
            res.close();

            if(res.first()) {
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

            //See if archer already registered
            if(getArcher(a.getFirstName(), a.getLastName()) != null) {
                System.out.println("Archer already registered, nothing done");
                return;
            }

            //Create statement and set the archer's values
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, a.getFirstName());
            pstmt.setString(2, a.getLastName());
            pstmt.setInt(3, a.getWeight());
            pstmt.setInt(4, a.getHeight());
            pstmt.setBoolean(5, a.isRightHanded());

            pstmt.execute();

            conn.close();

        } catch (SQLException e) {
            System.out.println("Bad response");
            e.printStackTrace();
        }

    }

    /*

    @return the Archer object if found, NULL if not found!
     */
    public Archer getArcher(String fName, String lName) {
        String query = "SELECT * FROM " + Archer.getTableName() + " WHERE Fname = \"" + fName +
                "\" AND Lname = \"" + lName + "\";" ;

        //Create connection
        try (Connection conn = DriverManager.getConnection(url, username, password)){

            //Create statement and set values into it
            PreparedStatement pstmt = conn.prepareStatement(query);

            ResultSet res = pstmt.executeQuery();

            if(res.wasNull() || !res.first())
                return null;

            Archer a = new Archer(
                    res.getString(2),   //Fname
                    res.getString(3),   //Lname
                    res.getInt(4),      //CoachId
                    res.getInt(5),      //Weight
                    res.getInt(6),      //Height
                    res.getBoolean(7)); //rightHanded

            conn.close();
            res.close();

            return a;

        } catch (SQLException e) {
            System.out.println("Bad response");
            e.printStackTrace();
        }

        return null;
    }

    public void deleteArcher(String fName, String lName) {

        String query = "DELETE FROM " + Archer.getTableName() + " WHERE Fname = \"" + fName +
                "\" AND Lname = \"" + lName + "\";" ;

        //Create connection
        try (Connection conn = DriverManager.getConnection(url, username, password)){

            //Create statement and execute
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.execute();

            conn.close();
        } catch (SQLException e) {
            System.out.println("Bad response");
            e.printStackTrace();
        }
    }

}
