package org.postnote.exception.repository;

/**
 * Base exception for Repository objects
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 */
public class RepositoryException extends Exception {

	private static final long serialVersionUID = 2688562793073247965L;

	public RepositoryException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}
