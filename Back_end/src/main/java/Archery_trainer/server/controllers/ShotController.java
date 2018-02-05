package Archery_trainer.server.controllers;

import Archery_trainer.server.databaseOperations.ArcherDatabaseOperations;
import Archery_trainer.server.databaseOperations.ShotDatabaseOperations;
import Archery_trainer.server.models.Archer;
import Archery_trainer.server.models.Shot;
import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class ShotController {

    /**
     * Get the shots that belong to a specified archer
     *
     * @param s     Json document describing the archer object whose shots to get
     * @return      List of Shot objects as a Json document
     */
    @RequestMapping(value = "/getShotsOfArcher", method = RequestMethod.POST)
    public String getShotsOfArcher(@RequestBody String s) {
        System.out.println("Recieved getShotsOfArcher request: " + s);

        try {
            //Create Archer object from the received json
            Gson gson = new Gson();
            Archer a = gson.fromJson(s, Archer.class);

            int archerNo = ArcherDatabaseOperations.getArcherNo(a.getEmail());

            List<Shot> shots = ShotDatabaseOperations.getShotsOfArcher(archerNo);

            String response = gson.toJson(shots);

            System.out.println("Sending response: " + response);

            return response;

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return "";
        }

    }

}