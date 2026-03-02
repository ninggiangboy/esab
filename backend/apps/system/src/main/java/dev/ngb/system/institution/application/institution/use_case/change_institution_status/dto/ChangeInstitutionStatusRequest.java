package dev.ngb.system.institution.application.institution.use_case.change_institution_status.dto;

import dev.ngb.domain.institution.model.InstitutionStatus;

public record ChangeInstitutionStatusRequest(
        InstitutionStatus status
) {
}
