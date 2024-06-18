package fr.alib.elec_boutique.services;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.alib.elec_boutique.services.storage.FileSystemStorageService;

@Service
public class MediaService {
	
	private FileSystemStorageService storageService;
	
	public MediaService(FileSystemStorageService storageService) {
		this.storageService = storageService;
	}
	
	/**
	 * Deletes media files.
	 * @param medias A list of media file names.
	 */
	public void deleteMedias(List<String> medias)
	{
		medias.forEach(m->{
			this.storageService.delete(m);
		});
	}
}
