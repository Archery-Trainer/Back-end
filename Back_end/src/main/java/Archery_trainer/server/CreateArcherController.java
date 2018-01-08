package Archery_trainer.server;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


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

        System.out.println("Created new archer: " + a.toString());

	//response:
	//@TODO: maybe we should just return an 'OK', but this shows
	//how to send objects back to app
	String response = gson.toJson(a, Archer.class);	

	return response;
    }

}
