package org.postnote.exception.repository;

/**
 * Thows when repository cannot insert an element.
 * 
 * @version 1.0 
 * @since 2014-06-12
 * 
 * @author Maurizio Nagni
 */
public class CannotInsertException extends RepositoryException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -411761553102349438L;

	public CannotInsertException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CannotInsertException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CannotInsertException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CannotInsertException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CannotInsertException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
