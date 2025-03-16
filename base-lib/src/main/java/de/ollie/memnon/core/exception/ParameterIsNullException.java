package de.ollie.memnon.core.exception;

public class ParameterIsNullException extends ServiceException {

	public static final String MESSAGE = "parameter cannot be null!";
	public static final String MESSAGE_ID = "parameter-is-null";
	public static final String PARAMETER_ATTRIBUTE_NAME = "attributeName";
	public static final String PARAMETER_ENTITY_NAME = "entityName";

	public ParameterIsNullException(String entityName, String attributeName) {
		super(MESSAGE, null, MESSAGE_ID, PARAMETER_ENTITY_NAME, entityName, PARAMETER_ATTRIBUTE_NAME, attributeName);
	}
}
