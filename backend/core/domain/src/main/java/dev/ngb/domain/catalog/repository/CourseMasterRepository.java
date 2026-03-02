package dev.ngb.domain.catalog.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.catalog.model.CourseMaster;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface CourseMasterRepository extends Repository<CourseMaster, UUID> {
    boolean existsByCode(String code);

    Optional<CourseMaster> findByCode(String code);

    boolean existsAllByCodes(Collection<String> codes);
}
