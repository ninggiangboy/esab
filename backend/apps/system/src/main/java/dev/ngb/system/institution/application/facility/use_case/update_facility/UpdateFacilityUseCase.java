package dev.ngb.system.institution.application.facility.use_case.update_facility;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.institution.error.FacilityError;
import dev.ngb.domain.institution.model.Facility;
import dev.ngb.domain.institution.repository.FacilityRepository;
import dev.ngb.system.institution.application.facility.use_case.update_facility.dto.UpdateFacilityRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateFacilityUseCase implements UseCaseService {

    private final FacilityRepository facilityRepository;

    public void execute(UUID facilityId, UpdateFacilityRequest request) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(FacilityError.FACILITY_NOT_FOUND::exception);
        facility.update(request.name(), request.address(), request.timezone(), request.status());
        facilityRepository.save(facility);
    }
}
