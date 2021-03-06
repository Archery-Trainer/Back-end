package Archery_trainer.server.databaseOperations;

import Archery_trainer.server.models.Archer;
import Archery_trainer.server.Main;

import java.sql.*;
import java.util.ArrayList;

public class ArcherDatabaseOperations {

    /**
     * Return the highest archer index in database
     *
     * @return The highest archer index
     * @throws SQLException
     */
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


    /**
     * Insert a new archer to the database
     *
     * @param a The archer to insert
     * @throws SQLException
     * @throws AlreadyRegisteredException
     */
    public static void insertArcher(Archer a) throws SQLException, AlreadyRegisteredException{

        String query = "INSERT INTO " + Archer.getTableName() +
                " (AthleteId, Fname, Lname, Weight, Height, RightHanded) VALUES (?, ?, ?, ?, ?, ?)";

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());

        //See if archer already registered
        if(selectArcher(a.getEmail()) != null) {
            conn.close();

            throw new AlreadyRegisteredException();
        }

        //Create statement and set the archer's values
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, a.getEmail());
        pstmt.setString(2, a.getFirstName());
        pstmt.setString(3, a.getLastName());
        pstmt.setInt(4, a.getWeight());
        pstmt.setInt(5, a.getHeight());
        pstmt.setBoolean(6, a.isRightHanded());

        pstmt.execute();

        conn.close();
    }

    /**
     * Get the index of an archer
     * @param email     Unique identifying string of the archer
     * @return          The index of the archer in the database
     * @throws SQLException
     */
    public static int getArcherNo(String email) throws SQLException {
        String query = "SELECT AthleteNo FROM " + Archer.getTableName() +
                " WHERE AthleteID = ";

        //Surround email with quotation marks
        boolean needQuote = email.charAt(0) != '"';
        if(needQuote)
            query += "\"" + email + "\"";
        else
            query += email + ";" ;


        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());


        PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);


        ResultSet res = pstmt.executeQuery();

        if(res.wasNull() || !res.first()) {
            conn.close();
            res.close();

            return -1;
        }

        int num = res.getInt(1);


        conn.close();
        res.close();

        return num;
    }


    /**
     * Find archer from database
     *
     * @param email     Unique email address of the archer to find
     * @return the Archer object if found, NULL if table was empty!
     */
    public static Archer selectArcher(String email) throws SQLException{

        String query = "SELECT * FROM " + Archer.getTableName() + " WHERE AthleteID = ";

        //Surround email with quotation marks
        boolean needQuote = email.charAt(0) != '"';
        if(needQuote)
            query += "\"" + email + "\"";
        else
            query += email + ";" ;

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());

        //Create statement and set values into it
        PreparedStatement pstmt = conn.prepareStatement(query);

        ResultSet res = pstmt.executeQuery();

        if(res.wasNull() || !res.first()) {
            System.out.println("Response was null");
            conn.close();
            res.close();

            return null;
        }

        Archer a = new Archer(
                res.getString(2),   //email
                res.getString(3),   //Fname
                res.getString(4),   //Lname
                res.getInt(5),      //CoachId
                res.getInt(6),      //Weight
                res.getInt(7),      //Height
                res.getBoolean(8)); //rightHanded

        conn.close();
        res.close();

        return a;
    }

    /**
     * Get all archers from the database
     *
     * @return  ArrayList of all the archers
     * @throws SQLException
     */
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
                    res.getString(2),   //email
                    res.getString(3),   //Fname
                    res.getString(4),   //Lname
                    res.getInt(5),      //CoachId
                    res.getInt(6),      //Weight
                    res.getInt(7),      //Height
                    res.getBoolean(8)); //rightHanded

            archers.add(a);
        }

        conn.close();
        res.close();

        return archers;
    }

    /**
     * Delete an archer from the database
     *
     * @param email     Email of the archer to delete
     * @throws SQLException
     */
    public static void deleteArcher(String email) throws  SQLException{

        String query = "DELETE FROM " + Archer.getTableName() + " WHERE Email = \"" + email + "\";" ;

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
