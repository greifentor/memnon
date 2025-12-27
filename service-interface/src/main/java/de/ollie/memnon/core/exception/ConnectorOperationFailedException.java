package de.ollie.memnon.core.exception;

public class ConnectorOperationFailedException extends RuntimeException {

	public ConnectorOperationFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
