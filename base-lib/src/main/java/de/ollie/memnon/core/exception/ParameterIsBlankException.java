package de.ollie.memnon.core.exception;

public class ParameterIsBlankException extends ServiceException {

	public static final String MESSAGE = "parameter cannot be blank!";
	public static final String MESSAGE_ID = "parameter-is-blank";
	public static final String PARAMETER_ATTRIBUTE_NAME = "attributeName";
	public static final String PARAMETER_ENTITY_NAME = "entityName";

	public ParameterIsBlankException(String entityName, String attributeName) {
		super(MESSAGE, null, MESSAGE_ID, PARAMETER_ENTITY_NAME, entityName, PARAMETER_ATTRIBUTE_NAME, attributeName);
	}
}
