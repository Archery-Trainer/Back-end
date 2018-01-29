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
public class StartRecordingController {


    //Recieve json, convert it to Archer object and return it
    @RequestMapping(value = "/startRecording", method = RequestMethod.POST)
    public ResponseEntity<?> startRecording(@RequestBody String jsonDocument) {

        System.out.println("Recieved createArcher request: " + jsonDocument);

        //Create request object
        Gson gson = new Gson();
        RecordingRequest req = gson.fromJson(jsonDocument, RecordingRequest.class);

        //Start recording
        boolean status= Recording.startRecording(req.getArcherEmail(), req.getTimestamp());

        if(status)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }
}