package dev.ngb.system.institution.application.institution.use_case.create_institution.dto;

public record CreateFacilityRequest(
        String facilityCode,
        String name,
        String address,
        String timezone
) {
}
