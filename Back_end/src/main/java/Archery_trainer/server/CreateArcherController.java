package Archery_trainer.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableAutoConfiguration
public class CreateArcherController {


    //Recieve Archer object and print it. Return OK.
    @RequestMapping("/createArcher")
    public String createArcher(){//@RequestBody Archer a) {

        System.out.println("Recieved: ");// + a.toString());

        return "HWEI";//a.toString();//new ResponseEntity<Archer>(a, HttpStatus.OK);

    }

}
