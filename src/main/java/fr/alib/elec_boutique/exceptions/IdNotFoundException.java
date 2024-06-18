package fr.alib.elec_boutique.exceptions;

public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public IdNotFoundException(String message) {
		super(message);
	}
	public IdNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
