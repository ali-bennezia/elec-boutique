package fr.alib.elec_boutique.exceptions.storage;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public StorageException(String message) {
		super(message);
	}
	public StorageException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
