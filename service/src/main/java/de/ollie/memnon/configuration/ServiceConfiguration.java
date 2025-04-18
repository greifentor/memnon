package de.ollie.memnon.configuration;

import lombok.Generated;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Generated
@Getter
public class ServiceConfiguration {

	@Value("${service.status.max.days.demnaechst:7}")
	private int statusMaxDaysDemaechst;

	@Value("${service.status.max.days.nah:3}")
	private int statusMaxDaysNah;
}
