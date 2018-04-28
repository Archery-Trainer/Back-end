package Archery_trainer.server;

import java.sql.*;
import java.util.ArrayList;

/**
 * Container class where to store the database address and credentials
 */
public class DatabaseCredentials {

    private String url;
    private String username;
    private String password;

    public DatabaseCredentials(String _url, String databaseName, String _username, String _password) {
        url = "jdbc:mysql://" + _url + "/" + databaseName;
        username = _username;
        password = _password;
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
