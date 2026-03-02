package dev.ngb.domain.institution.error;

import dev.ngb.domain.DomainError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InstitutionError implements DomainError {

    INSTITUTION_NOT_FOUND("Institution not found"),
    INSTITUTION_CODE_ALREADY_EXISTS("Institution code already exists"),
    DUPLICATE_FACILITY_CODE("Duplicate facility code in request"),
    NOT_IN_SETUP_STATUS("Institution is not in SETUP status"),
    CANNOT_TRANSITION_TO_SETUP("Cannot transition to SETUP status"),
    INVALID_STATUS_TRANSITION("Institution is currently in SETUP status, use activate instead");

    private final String message;
}
