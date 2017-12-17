package Archery_trainer.server;

import Archery_trainer.mqttClient.MqttMessageHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.ComponentScan;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = {"Archery_trainer.server"})
public class Main {

	private static MqttMessageHandler messageHandler;

	@RequestMapping( "/test" )
	String test() {
		System.out.println("test() called");
		return "This is a test";
	}

	public static void main( String[] args ) throws Exception {
		System.out.println("Starting app...");
		SpringApplication.run( Main.class, args );

		if(messageHandler == null)
			messageHandler = new MqttMessageHandler();


		//I quess Spring automatically spawns threads to handle
		//HTTP-requests, so we can loop like this in the main thread
		while(true) {
			String res = messageHandler.getNewestMessage();

			if (res.length() != 0)
				System.out.println(res);

			Thread.sleep(500);
		}

	}
}
