package Archery_trainer.server.databaseOperations;

import Archery_trainer.server.models.Archer;
import Archery_trainer.server.Main;

import java.sql.*;
import java.util.ArrayList;

public class ArcherDatabaseOperations {

    //Return the highest archer id in database
    public static int getMaxIndexArcher() throws SQLException {

        String query = "SELECT MAX(Id) FROM " + Archer.getTableName() + ";";


        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());

        Statement ps = conn.createStatement();
        ResultSet resSet = ps.executeQuery(query);

        conn.close();
        resSet.close();

        int res = -1;

        if(resSet.first())
            res = resSet.getInt(1);

        return res;
    }


    public static void insertArcher(Archer a) throws SQLException, AlreadyRegisteredException{

        String query = "INSERT INTO " + Archer.getTableName() +
                " (Fname, Lname, Weight, Height, RightHanded) VALUES (?, ?, ?, ?, ?)";

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());

        //See if archer already registered
        if(selectArcher(a.getFirstName(), a.getLastName()) != null) {
            conn.close();

            throw new AlreadyRegisteredException();
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
    }

    /**
     * Find archer from database
     *
     * @param fName     First name of the archer to find
     * @param lName     Last name of the archer to find
     * @return the Archer object if found, NULL if table was empty!
     */
    public static Archer selectArcher(String fName, String lName) throws SQLException{

        String query = "SELECT * FROM " + Archer.getTableName() + " WHERE Fname = \"" + fName +
                "\" AND Lname = \"" + lName + "\";" ;

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());

        //Create statement and set values into it
        PreparedStatement pstmt = conn.prepareStatement(query);

        ResultSet res = pstmt.executeQuery();

        if(res.wasNull() || !res.first()) {
            conn.close();
            res.close();

            return null;
        }

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
    }

    public static ArrayList<Archer> selectAllArchers() throws SQLException {

        ArrayList<Archer> archers = new ArrayList<>();

        String query = "SELECT * FROM " + Archer.getTableName() + ";";

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());

        //Create statement and execute
        PreparedStatement pstmt = conn.prepareStatement(query);

        ResultSet res = pstmt.executeQuery();

        if(res.wasNull()) {
            conn.close();
            res.close();

            return archers;
        }


        //Add all archers to arraylist
        while(res.next()) {
            Archer a = new Archer(
                    res.getString(2),   //Fname
                    res.getString(3),   //Lname
                    res.getInt(4),      //CoachId
                    res.getInt(5),      //Weight
                    res.getInt(6),      //Height
                    res.getBoolean(7)); //rightHanded

            archers.add(a);
        }

        conn.close();
        res.close();

        return archers;
    }

    public static void deleteArcher(String fName, String lName) throws  SQLException{

        String query = "DELETE FROM " + Archer.getTableName() + " WHERE Fname = \"" + fName +
                "\" AND Lname = \"" + lName + "\";" ;

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());

        //Create statement and execute
        PreparedStatement pstmt = conn.prepareStatement(query);

        pstmt.execute();

        conn.close();

    }

}
