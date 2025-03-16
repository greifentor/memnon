package de.ollie.memnon.core.exception;

public class NoSuchRecordException extends ServiceException {

	public static final String MESSAGE = "no record with passed id found!";
	public static final String MESSAGE_ID = "no-such-record";
	public static final String PARAMETER_ID_ATTRIBUTE_NAME = "idAttributeName";
	public static final String PARAMETER_ENTITY_NAME = "entityName";
	public static final String PARAMETER_VALUE_NAME = "value";

	public NoSuchRecordException(String value, String entityName, String idAttributeName) {
		super(
			MESSAGE,
			null,
			MESSAGE_ID,
			PARAMETER_ENTITY_NAME,
			entityName,
			PARAMETER_ID_ATTRIBUTE_NAME,
			idAttributeName,
			PARAMETER_VALUE_NAME,
			value
		);
	}
}
