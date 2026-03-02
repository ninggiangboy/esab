package dev.ngb.system.institution.application.institution.use_case.create_institution.dto;

import java.util.List;

public record CreateInstitutionRequest(
        String code,
        String name,
        String timezone,
        List<CreateFacilityRequest> facilities
) {
}
