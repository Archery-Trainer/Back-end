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
	
	@RequestMapping( "/test" )
	String test() {
		System.out.println("test() called");
		return "This is a test";
	}

	public static void main( String[] args ) throws Exception {
		System.out.println("Starting app...");
		SpringApplication.run( Main.class, args );
	}
}
