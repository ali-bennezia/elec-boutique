package fr.alib.elec_boutique.exceptions;

public class AddConflictException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public AddConflictException(String message) {
		super(message);
	}
	public AddConflictException(String message, Throwable cause) {
		super(message, cause);
	}
}
