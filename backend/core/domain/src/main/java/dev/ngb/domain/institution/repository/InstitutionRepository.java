package dev.ngb.domain.institution.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.institution.model.Institution;

import java.util.UUID;

public interface InstitutionRepository extends Repository<Institution, UUID> {
    boolean existsByCode(String code);
}
