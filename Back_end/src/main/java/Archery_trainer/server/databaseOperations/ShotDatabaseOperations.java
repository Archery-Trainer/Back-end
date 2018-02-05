package Archery_trainer.server.databaseOperations;

import Archery_trainer.server.Main;
import Archery_trainer.server.models.Shot;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ShotDatabaseOperations {

    /**
     * Store a shot entry into database
     * @param email     Email of the archer
     * @return id of the stored shot
     * @throws SQLException
     */
    //@TODO: Should this be called with a Shot object?
    public static int insertShot(String email, long timestamp) throws SQLException {

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());


        String query = "INSERT INTO " + Shot.getTableName() +
                " (Date, Time, Score, AthleteNo) VALUES (?, ?, ?, ?)";

        int archerNo = ArcherDatabaseOperations.getArcherNo(email);

        System.out.println("Storing a shot for archer number " + archerNo);

        //Create statement and set the values
        PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setDate(1, new Date(timestamp));
        pstmt.setTime(2, new Time(timestamp));
        pstmt.setInt(3, 0);
        pstmt.setInt(4, archerNo);

        pstmt.execute();

        //Get the id of the created shot entry
        ResultSet res = pstmt.getGeneratedKeys();
        int shotId;
        if(res.first())
            shotId = res.getInt(1);
        else
            throw new SQLException();

        conn.close();

        return shotId;
    }

    public static void insertScoreToShot(int shotId, int score) throws SQLException {
        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());


        String query = "UPDATE " + Shot.getTableName() + " SET Score = "
                + score + " WHERE ShotID = " + shotId + ";";


        System.out.println("Setting score of shot " + shotId + " to " + score);

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.execute();

        conn.close();

    }

    public static List<Shot> getShotsOfArcher(int archerNo) throws SQLException {
        List<Shot> shots = new LinkedList<>();

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());


        String query = "SELECT * FROM " + Shot.getTableName() +
                " WHERE AthleteNo=" + archerNo + ";";

        System.out.println("Fetching all shots of archer with id " + archerNo);

        PreparedStatement pstmt = conn.prepareStatement(query);

        ResultSet res = pstmt.executeQuery();

        if(res.wasNull()) {
            conn.close();
            res.close();

            return shots;
        }

        //Add all shots to a list
        while(res.next()) {
            Shot s = new Shot(
                    res.getInt(1),
                    res.getDate(2),
                    res.getTime(3),
                    res.getInt(4),
                    res.getInt(5)
            );

            shots.add(s);
        }

        conn.close();
        res.close();

        return shots;
    }
}
