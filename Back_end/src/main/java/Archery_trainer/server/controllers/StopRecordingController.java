package Archery_trainer.server.controllers;

import Archery_trainer.server.Recording;
import Archery_trainer.server.models.RecordingRequest;
import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class StopRecordingController {


    //Recieve json, convert it to Archer object and return it
    @RequestMapping(value = "/stopRecording", method = RequestMethod.POST)
    public ResponseEntity<?> stopRecording(@RequestBody String jsonDocument) {

        System.out.println("Recieved stopRecording request: " + jsonDocument);

        //Create request object
        Gson gson = new Gson();
        RecordingRequest req = gson.fromJson(jsonDocument, RecordingRequest.class);

        //Start recording
        Recording.stopRecording(req);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}