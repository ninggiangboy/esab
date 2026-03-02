package dev.ngb.system.institution.web;

import dev.ngb.system.institution.application.facility.use_case.add_facility.AddFacilityUseCase;
import dev.ngb.system.institution.application.facility.use_case.add_facility.dto.AddFacilityRequest;
import dev.ngb.system.institution.application.facility.use_case.update_facility.UpdateFacilityUseCase;
import dev.ngb.system.institution.application.facility.use_case.update_facility.dto.UpdateFacilityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FacilityResource implements FacilityApi {

    private final AddFacilityUseCase addFacilityUseCase;
    private final UpdateFacilityUseCase updateFacilityUseCase;

    @Override
    public void add(AddFacilityRequest request) {
        addFacilityUseCase.execute(request);
    }

    @Override
    public void update(UUID id, UpdateFacilityRequest request) {
        updateFacilityUseCase.execute(id, request);
    }
}
