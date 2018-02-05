package Archery_trainer.server.controllers;

import Archery_trainer.server.DatabaseCredentials;
import Archery_trainer.server.databaseOperations.AlreadyRegisteredException;
import Archery_trainer.server.models.Archer;
import Archery_trainer.server.databaseOperations.ArcherDatabaseOperations;
import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;


@RestController
@EnableAutoConfiguration
public class ArcherController {

    /**
     * Create an archer into database
     *
     * @param s     Json document describing an Archer object
     * @return  The same archer object
     */
    @RequestMapping(value = "/createArcher", method = RequestMethod.POST)
    public String createArcher(@RequestBody String s) {


        System.out.println("Recieved createArcher request: " + s);

        //Create Archer object
        Gson gson = new Gson();
        Archer a = gson.fromJson(s, Archer.class);

        //Store archer to database
        try {
            ArcherDatabaseOperations.insertArcher(a);
        } catch (SQLException e) {
            System.out.println("Unable to insert archer into database");
            e.printStackTrace();

            return "";
        }  catch (AlreadyRegisteredException e) {
            System.out.println("Archer of name " + a.getFirstName() + " " + a.getLastName()
                    + "already registered");
            e.printStackTrace();
        }

        System.out.println("Created new archer: " + a.toString());

        //response:
        //@TODO: maybe we should just return an 'OK', but this shows
        //how to send objects back to app
        String response = gson.toJson(a, Archer.class);

        return response;
    }


    /**
     * Get an Archer object from the database
     *
     * @param email     E-mail of the archer to get
     * @return  Archer object
     */
    @RequestMapping(value = "/getArcher", method = RequestMethod.POST)
    public String getArcher(@RequestBody String email) {

        String response = "";

        System.out.println("Recieved getArcher request: " + email);

        Archer a = null;
        try {
            a = ArcherDatabaseOperations.selectArcher(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return response;
        }

        if(a == null)
            return response;

        System.out.println("Created new archer: " + a.toString());

        Gson gson = new Gson();
        response = gson.toJson(a, Archer.class);

        return response;
    }


    @RequestMapping(value = "/getAllArchers")
    public String getAllArchers() {
        String response = "";

        System.out.println("Recieved getAllArcher request");

        try {
            List<Archer> archers = ArcherDatabaseOperations.selectAllArchers();

            Gson gson = new Gson();
            response = gson.toJson(archers);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;


    }

}
