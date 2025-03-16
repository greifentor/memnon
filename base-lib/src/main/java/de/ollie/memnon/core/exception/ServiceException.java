package de.ollie.memnon.core.exception;

import static de.ollie.memnon.util.Check.ensure;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

	private final String messageId;
	private final Map<String, String> messageData = new HashMap<>();

	public ServiceException(String message, Throwable cause, String messageId, String... messageData) {
		super(message, cause);
		ensure(
			messageData.length % 2 == 0,
			new IllegalStateException(
				"exception requires an even number of message data: " + messageData.length + " arguments"
			)
		);
		this.messageId = messageId;
		for (int i = 0; i < messageData.length; i = i + 2) {
			this.messageData.put(messageData[i], messageData[i + 1]);
		}
	}
}
