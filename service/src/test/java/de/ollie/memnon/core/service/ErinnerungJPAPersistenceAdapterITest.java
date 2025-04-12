package de.ollie.memnon.core.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.ollie.memnon.core.model.ErinnerungId;
import de.ollie.memnon.core.model.WiederholungJaehrlich;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
	properties = {
		"spring.liquibase.change-log=classpath:liquibase/change-log-master.xml",
		"spring.datasource.url=jdbc:hsqldb:mem:carp-bm",
	}
)
class ErinnerungDBORepositoryITest {

	private static final String NAME_0 = "name-0";
	private static final String NAME_1 = "name-1";

	@Autowired
	private ErinnerungService unitUnderTest;

	@Test
	void testFindIdsByNameContains() {
		// Prepare
		ErinnerungId id0 = unitUnderTest.erzeugeErinnerung(
			NAME_0,
			LocalDate.now(),
			new WiederholungJaehrlich(),
			LocalDate.now().minusYears(20)
		);
		unitUnderTest.erzeugeErinnerung(
			NAME_1,
			LocalDate.now(),
			new WiederholungJaehrlich(),
			LocalDate.now().minusYears(20)
		);
		// Run
		List<ErinnerungId> result = unitUnderTest.findeAlleErinnerungIdZuSuchstring(NAME_0.substring(3));
		// Check
		assertTrue(!result.isEmpty());
		assertEquals(id0, result.get(0));
	}
}
