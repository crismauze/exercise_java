package ec.edu.cbenalcazar.exercisejava.exceptions;

public class RutaException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public RutaException(String message) {
		super(message);
	}
	
	public RutaException(String message, Throwable cause) {
		super(message, cause);
	}

}
