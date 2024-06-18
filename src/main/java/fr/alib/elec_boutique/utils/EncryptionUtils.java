package fr.alib.elec_boutique.utils;

import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix="encr")
@ConfigurationPropertiesScan
public class EncryptionUtils {

	@Value("${encr.encryptor-password}")
	private String encryptorPassword;
	
	@Bean
	BytesEncryptor encryptor()
	{
		return Encryptors.standard(this.encryptorPassword, KeyGenerators.string().generateKey());
	}
	
	public byte[] longToBytes(Long data)
	{
		ByteBuffer buff = ByteBuffer.allocate( Long.BYTES );
		buff.putLong(data);
		return buff.array();
	}
	
	public Long bytesToLong(byte[] data)
	{
		ByteBuffer buff = ByteBuffer.allocate( Long.BYTES );
		buff.put(data);
		buff.flip();
		return buff.getLong();
	}

}
