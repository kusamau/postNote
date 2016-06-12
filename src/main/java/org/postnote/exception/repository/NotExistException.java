package org.postnote.exception.repository;

/**
 * Thows when repository cannot update an element.
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 */
public class NotExistException extends RepositoryException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -554078391212104454L;

	public NotExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NotExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NotExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
