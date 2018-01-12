package Archery_trainer.server.controllers;

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


@RestController
@EnableAutoConfiguration
public class CreateArcherController {

    //Recieve json, convert it to Archer object and return it
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

}
