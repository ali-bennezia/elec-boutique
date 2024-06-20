package fr.alib.elec_boutique.exceptions;

public class LackingAuthorizationsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public LackingAuthorizationsException(String message) {
		super(message);
	}
	public LackingAuthorizationsException(String message, Throwable cause) {
		super(message, cause);
	}
}
