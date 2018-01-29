package Archery_trainer.server.databaseOperations;

import Archery_trainer.server.Main;
import Archery_trainer.server.models.MeasuredDataSet;
import Archery_trainer.server.models.SensorData;

import java.sql.*;

public class ShotDatabaseOperations {

    //@TODO: Should there be a class for Shot?

    /**
     * Store a shot entry into database
     * @param email     Email of the archer
     * @return id of the stored shot
     * @throws SQLException
     */
    public static int insertShot(String email, long timestamp) throws SQLException {

        //Create connection
        Connection conn = DriverManager.getConnection(
                Main.databaseCredentials.getUrl(),
                Main.databaseCredentials.getUsername(),
                Main.databaseCredentials.getPassword());



        String query = "INSERT INTO Shot" +
                " (Date, Time, Score, AthleteNo) VALUES (?, ?, ?, ?)";

        int archerNo = ArcherDatabaseOperations.getArcherNo(email);

        System.out.println("Storing a shot for archer number " + archerNo);

        //Create statement and set the values
        PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setDate(1, new Date(timestamp));
        pstmt.setTime(2, new Time(timestamp));
        pstmt.setInt(3, 0);     //@TODO how to handle scores
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




}
