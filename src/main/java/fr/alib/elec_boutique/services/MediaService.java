package fr.alib.elec_boutique.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.alib.elec_boutique.services.storage.FileSystemStorageService;

@Service
public class MediaService {
	
	private FileSystemStorageService storageService;
	
	public MediaService(FileSystemStorageService storageService) {
		this.storageService = storageService;
	}
	
	/**
	 * Stores a file.
	 * @param files The file.
	 * @return The newly stored media file name.
	 */
	public String storeFile(MultipartFile file)
	{
		return this.storageService.store(file);
	}
	
	/**
	 * Stores files.
	 * @param files The files.
	 * @return The newly stored media file names.
	 */
	public List<String> storeFiles(List<MultipartFile> files)
	{
		List<String> result = files.stream().map(f->{
			return storeFile(f);
		}).collect(Collectors.toList());
		return result;
	}
	
	/**
	 * Loads a file as a Resource object.
	 * @param media The media's file name.
	 * @return The Resource object.
	 */
	public Resource loadMediaAsResource(String media)
	{
		return this.storageService.loadAsResource(media);
	}
	
	/**
	 * Loads files as a Resource objects.
	 * @param media The medias' file names.
	 * @return The Resource objects.
	 */
	public List<Resource> loadMediasAsResources(List<String> medias)
	{
		return medias.stream().map(m -> {
			return loadMediaAsResource(m);
		}).collect(Collectors.toList());
	}
	
	/**
	 * Deletes a media file.
	 * @param fileName The media file's name.
	 */
	public void deleteMedia(String fileName)
	{
		this.storageService.delete(fileName);
	}
	
	/**
	 * Deletes media files.
	 * @param medias A list of media file names.
	 */
	public void deleteMedias(List<String> medias)
	{
		medias.forEach(m->{
			deleteMedia(m);
		});
	}
}
