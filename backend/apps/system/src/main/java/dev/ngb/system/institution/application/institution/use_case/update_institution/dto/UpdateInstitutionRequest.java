package dev.ngb.system.institution.application.institution.use_case.update_institution.dto;

import dev.ngb.domain.institution.model.InstitutionStatus;

public record UpdateInstitutionRequest(
        String name,
        String timezone,
        InstitutionStatus status
) {
}
