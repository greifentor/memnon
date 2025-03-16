package de.ollie.memnon.core.exception;

import java.util.Arrays;

public class UniqueConstraintViolationException extends ServiceException {

	public static final String MESSAGE = "unique constraint violated!";
	public static final String MESSAGE_ID = "unique-constraint-violated";
	public static final String PARAMETER_ATTRIBUTES_NAMES = "attributeNames";
	public static final String PARAMETER_ENTITY_NAME = "entityName";
	public static final String PARAMETER_VALUES_NAME = "values";

	public UniqueConstraintViolationException(String values, String entityName, String... attributeNames) {
		super(
			MESSAGE,
			null,
			MESSAGE_ID,
			PARAMETER_ENTITY_NAME,
			entityName,
			PARAMETER_ATTRIBUTES_NAMES,
			Arrays.asList(attributeNames).stream().reduce((s0, s1) -> s0 + "," + s1).orElse("-"),
			PARAMETER_VALUES_NAME,
			values
		);
	}
}
