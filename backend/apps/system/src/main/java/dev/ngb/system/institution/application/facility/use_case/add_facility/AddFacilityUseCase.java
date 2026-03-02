package dev.ngb.system.institution.application.facility.use_case.add_facility;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.institution.error.FacilityError;
import dev.ngb.domain.institution.error.InstitutionError;
import dev.ngb.domain.institution.model.Facility;
import dev.ngb.domain.institution.model.Institution;
import dev.ngb.domain.institution.repository.FacilityRepository;
import dev.ngb.domain.institution.repository.InstitutionRepository;
import dev.ngb.system.institution.application.facility.use_case.add_facility.dto.AddFacilityRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddFacilityUseCase implements UseCaseService {

    private final InstitutionRepository institutionRepository;
    private final FacilityRepository facilityRepository;

    public void execute(AddFacilityRequest request) {
        Institution institution = institutionRepository.findById(request.institutionId())
                .orElseThrow(InstitutionError.INSTITUTION_NOT_FOUND::exception);

        if (facilityRepository.existsByFacilityCodeAndInstitutionId(
                request.facilityCode(), institution.getId())) {
            throw FacilityError.FACILITY_CODE_ALREADY_EXISTS.exception();
        }

        Facility facility = Facility.create(
                institution.getId(),
                request.facilityCode(),
                institution.getCode(),
                request.name(),
                request.address(),
                request.timezone()
        );
        facilityRepository.save(facility);
    }
}
