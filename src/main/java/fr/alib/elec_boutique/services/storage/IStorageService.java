package fr.alib.elec_boutique.services.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

	void init();
	
	String store(MultipartFile file);
	
	Stream<Path> loadAll();
	
	Path load(String fileName);
	
	void delete(String fileName);
	
	Resource loadAsResource(String fileName);
	
	void deleteAll();
	
}
