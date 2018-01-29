package Archery_trainer.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.ComponentScan;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = {"Archery_trainer.server"})
public class Main {

	public static DatabaseCredentials databaseCredentials;

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

	}
}
