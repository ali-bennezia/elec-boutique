package fr.alib.elec_boutique.exceptions.storage;

public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = 1L;
	
	public StorageFileNotFoundException(String message) {
		super(message);
	}
	public StorageFileNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
