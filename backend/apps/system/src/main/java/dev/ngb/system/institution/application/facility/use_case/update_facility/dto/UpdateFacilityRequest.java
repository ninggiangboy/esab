package dev.ngb.system.institution.application.facility.use_case.update_facility.dto;

import dev.ngb.domain.institution.model.FacilityStatus;

public record UpdateFacilityRequest(
        String name,
        String address,
        String timezone,
        FacilityStatus status
) {
}
