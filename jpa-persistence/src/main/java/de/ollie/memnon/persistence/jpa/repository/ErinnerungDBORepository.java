package de.ollie.memnon.persistence.jpa.repository;

import de.ollie.memnon.persistence.jpa.entity.ErinnerungDBO;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErinnerungDBORepository extends JpaRepository<ErinnerungDBO, UUID> {}
