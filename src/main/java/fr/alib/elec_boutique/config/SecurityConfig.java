package fr.alib.elec_boutique.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception
	{
		return http.build();
	}
	
}
