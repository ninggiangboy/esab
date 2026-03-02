package dev.ngb.domain.institution.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.institution.model.Facility;

import java.util.UUID;

public interface FacilityRepository extends Repository<Facility, UUID> {
    boolean existsByFacilityCodeAndInstitutionId(String facilityCode, UUID institutionId);
}
