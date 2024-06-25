package fr.alib.elec_boutique.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alib.elec_boutique.exceptions.AddConflictException;
import fr.alib.elec_boutique.exceptions.IdNotFoundException;
import fr.alib.elec_boutique.exceptions.storage.StorageFileNotFoundException;
import fr.alib.elec_boutique.services.MediaService;

@RestController
@RequestMapping("/api/medias")
public class MediaController {

	@Autowired
	private MediaService mediaService;
	
	@GetMapping(value = "/{media}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getMedia(@PathVariable("media") String mediaName) throws StorageFileNotFoundException, IOException
	{
		Resource media = this.mediaService.loadMediaAsResource(mediaName);
		return ResponseEntity
				.status(HttpStatus.OK)
				.contentLength(media.contentLength())
				.body(media.getContentAsByteArray());
	}
	
	@ExceptionHandler({UsernameNotFoundException.class, IdNotFoundException.class, StorageFileNotFoundException.class})
	public ResponseEntity<?> notFoundHandler()
	{
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<?> badCredentialsHandler()
	{
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@ExceptionHandler(AddConflictException.class)
	public ResponseEntity<?> conflictHandler()
	{
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
}
