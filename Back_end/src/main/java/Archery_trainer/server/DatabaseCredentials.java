package Archery_trainer.server;

import java.sql.*;
import java.util.ArrayList;

/**
 * Container class where to store the database address and credentials
 */
public class DatabaseCredentials {

    private final String urlDatabaseServer = "archerytrainerdb.cl9ugyt9v9y1.us-east-1.rds.amazonaws.com";

    private String url;
    private String databaseName;
    private String username;
    private String password;

    public DatabaseCredentials(String _databaseName, String _username, String _password) {
        databaseName = _databaseName;
        username = _username;
        password = _password;
        url = "jdbc:mysql://" + urlDatabaseServer + "/" + databaseName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}