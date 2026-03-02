package dev.ngb.domain.institution.error;

import dev.ngb.domain.DomainError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FacilityError implements DomainError {

    FACILITY_NOT_FOUND("Facility not found"),
    FACILITY_CODE_ALREADY_EXISTS("Facility code already exists in this institution");

    private final String message;
}
