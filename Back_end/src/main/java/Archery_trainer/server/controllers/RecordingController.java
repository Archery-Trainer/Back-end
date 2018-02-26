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
public class RecordingController {


    /**
     * Ask the server to start recording the MQTT-messages
     *
     * @param jsonDocument  An instance of Recording request
     * @return  Http status
     */
    @RequestMapping(value = "/startRecording", method = RequestMethod.POST)
    public ResponseEntity<?> startRecording(@RequestBody String jsonDocument) {

        System.out.println("Recieved startRecording request: " + jsonDocument);

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


    /**
     * Ask the server to stop recording the MQTT-messages
     *
     * @return  Id of the created Shot row in the database
     */
    @RequestMapping(value = "/stopRecording", method = RequestMethod.POST)
    public int stopRecording() {

        System.out.println("Recieved stopRecording request");

        //Stop recording
        int shotId = Recording.stopRecording();

        return shotId;
    }
}
