package fr.alib.elec_boutique.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) 
	{
		registry.addViewController("/notFound").setViewName("forward:/");
	}
	
	@Bean
	WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer()
	{
		return container -> {
			container.addErrorPages( new ErrorPage(HttpStatus.NOT_FOUND, "/notFound") );
		};
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) 
	{
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:4200")
		.allowedMethods("GET", "POST", "PUT", "DELETE");
	}
	

}
