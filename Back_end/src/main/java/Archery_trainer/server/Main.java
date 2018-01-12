package Archery_trainer.server;

import Archery_trainer.mqttClient.MqttMessageHandler;
import Archery_trainer.server.databaseOperations.AlreadyRegisteredException;
import Archery_trainer.server.databaseOperations.ArcherDatabaseOperations;
import Archery_trainer.server.models.Archer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.ComponentScan;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = {"Archery_trainer.server"})
public class Main {

	public static DatabaseCredentials databaseCredentials;
	private static MqttMessageHandler messageHandler;

	@RequestMapping( "/test" )
	String test() {
		System.out.println("test() called");
		return "This is a test";
	}

	public static void main( String[] args ) throws Exception {

		if(args.length == 3) {
			databaseCredentials = new DatabaseCredentials(args[0], args[1], args[2]);
		} else {
			System.out.println("Arguments: <database name> <database username> <database password>");
			return;
		}


		System.out.println("Starting server...");

		SpringApplication.run( Main.class, args );

		//Test inserting an archer
		try{
			Archer a = new Archer("Marita", "Taavitsainen", -1, 1, 3, false);
			ArcherDatabaseOperations.insertArcher(a);

		} catch (SQLException e) {
			System.out.println("Unable to create archer");
			e.printStackTrace();
		}
		catch (AlreadyRegisteredException e) {
			System.out.println("Archer already registered");
		}

		//Test listing all archers
		ArrayList<Archer> allArchers;
		try{
			allArchers = ArcherDatabaseOperations.selectAllArchers();

			for(Archer a : allArchers)
				System.out.println(a.toString());

		} catch (SQLException e) {
			System.out.println("Unable to find archers");
			e.printStackTrace();
		}

		//Start MQTT-client
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
