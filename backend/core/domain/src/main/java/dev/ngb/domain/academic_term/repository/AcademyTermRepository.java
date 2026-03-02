package dev.ngb.domain.academic_term.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.academic_term.model.AcademyTerm;

import java.util.UUID;

public interface AcademyTermRepository extends Repository<AcademyTerm, UUID> {
    boolean existsByCodeAndFacilityId(String code, UUID facilityId);
}
