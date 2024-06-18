package fr.alib.elec_boutique.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties("storage")
@ConfigurationPropertiesScan
public class StorageProperties {
	
	private String location = "uploads";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
