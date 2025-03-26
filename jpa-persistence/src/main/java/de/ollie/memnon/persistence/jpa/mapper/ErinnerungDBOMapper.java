package de.ollie.memnon.persistence.jpa.mapper;

import de.ollie.memnon.core.model.Erinnerung;
import de.ollie.memnon.core.service.WiederholungService;
import de.ollie.memnon.persistence.jpa.entity.ErinnerungDBO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ErinnerungDBOMapper {
	@Mapping(target = "id", expression = "java(new de.ollie.memnon.core.model.ErinnerungId(dbo.getId()))")
	@Mapping(
		target = "wiederholung",
		expression = "java(wiederholungService.holeWiederholungMitNamen(dbo.getWiederholungName()).orElse(null))"
	)
	Erinnerung toModel(ErinnerungDBO dbo, @Context WiederholungService wiederholungService);

	@Mapping(target = "id", expression = "java(model.getId() != null ? model.getId().getUuid() : null)")
	@Mapping(
		target = "wiederholungName",
		expression = "java(model.getWiederholung() != null ? model.getWiederholung().getName() : null)"
	)
	ErinnerungDBO toDbo(Erinnerung model);
}
