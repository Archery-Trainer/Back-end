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


    //Recieve Archer object and print it. Return OK.
    @RequestMapping(value = "/createArcher", method = RequestMethod.POST)
    public Archer get(@RequestBody String s) {

        Gson gson = new Gson();
        Archer a = gson.fromJson(s, Archer.class);

        System.out.println("Created new archer: " + a.toString());

        return a;
    }

}
