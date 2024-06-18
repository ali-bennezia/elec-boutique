package fr.alib.elec_boutique.services.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import fr.alib.elec_boutique.exceptions.storage.StorageException;
import fr.alib.elec_boutique.exceptions.storage.StorageFileNotFoundException;
import fr.alib.elec_boutique.properties.StorageProperties;

@Service
public class FileSystemStorageService implements IStorageService {

	private final Path rootLocation;
	
	public FileSystemStorageService(StorageProperties properties)
	{
		if (properties.getLocation().trim().length() == 0)
			throw new StorageException("File upload property can't be empty.");
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Couldn't initialize storage.", e);
		}
	}

	/**
	 * Stores a file.
	 * @param file The file.
	 * @return The newly stored file's name, including extension.
	 */
	@Override
	public String store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Can not store empty files.");
			}
			String originalFileName = file.getOriginalFilename();
			String extension = FilenameUtils.getExtension( originalFileName );
			String destinationFileName = UUID.randomUUID().toString() + "." + extension;
			Path destinationFile = this.rootLocation.resolve(destinationFileName).normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new StorageException("Can not store files outside of root directory.");
			}
			try (InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
			return destinationFileName;
		} catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (IOException e) {
			throw new StorageException("Failed to load files.", e);
		}
	}

	@Override
	public Path load(String fileName) {
		return this.rootLocation.resolve(fileName);
	}
	
	@Override 
	public void delete(String fileName) {
		try {
			Path file = load(fileName);
			if ( !file.getParent().toAbsolutePath().equals(this.rootLocation.toAbsolutePath()) )
				throw new StorageException("Can't delete file outside of root directory.");
			Files.deleteIfExists(file);
		} catch (IOException e) {
			throw new StorageException("Failed to delete file: " + fileName, e);
		}

	}
	
	@Override
	public Resource loadAsResource(String fileName) {
		try {
			Path file = load(fileName);
			if ( !file.getParent().toAbsolutePath().equals(this.rootLocation.toAbsolutePath()) )
				throw new StorageException("Can't load file outside of root directory.");
			Resource resource = new UrlResource(file.toUri());
			if ( resource.exists() || resource.isReadable() ) {
				return resource;
			}else {
				throw new StorageFileNotFoundException("Couldn't find file: " + fileName);
			}
		} catch (MalformedURLException e) {
			throw new StorageException("Couldn't read file: " + fileName);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(this.rootLocation.toFile());
	}



}
