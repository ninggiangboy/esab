package dev.ngb.system.institution.application.facility.use_case.add_facility.dto;

import java.util.UUID;

public record AddFacilityRequest(
        UUID institutionId,
        String facilityCode,
        String name,
        String address,
        String timezone
) {
}
