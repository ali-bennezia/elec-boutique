package fr.alib.elec_boutique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElecBoutiqueApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElecBoutiqueApplication.class, args);
	}
	
	/*
	@Bean
	CommandLineRunner cmdLineRunner(UserService userService)
	{
		return (args)->{
			userService.deleteUserById(1L);
		};
	}
	*/

}
