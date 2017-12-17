package Archery_trainer.server;

import Archery_trainer.mqttClient.MqttMessageHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@EnableAutoConfiguration
/*
* This controller handles incoming POST, GET and PUT messages to http://<address-of-machine>:8080/message 
*
*/
public class MessageController
{
	private final AtomicLong counter = new AtomicLong(0);

	@RequestMapping("/message")
	Message createAndSendMessage() {

		Message msg1 = new Message(counter.addAndGet(1), MessageType.SENSORVALUE, "Heippahei!");

		return msg1; //Builds JSON automatically
	}
	
}
