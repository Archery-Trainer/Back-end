package Archery_trainer.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
/*
* This controller handles incoming POST, GET and PUT messages to http://<address-of-machine>:8080/message 
*
*/
public class MessageController
{

	@RequestMapping("/message")
	String getMessage() {
		Message msg = new Message(0, MessageType.SENSORVALUE, "Heippahei!");
		return "Moi";
	}
	
}
