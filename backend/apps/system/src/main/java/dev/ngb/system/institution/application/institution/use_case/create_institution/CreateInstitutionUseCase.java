package dev.ngb.system.institution.application.institution.use_case.create_institution;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.institution.model.Facility;
import dev.ngb.domain.institution.model.Institution;
import dev.ngb.domain.institution.error.InstitutionError;
import dev.ngb.domain.institution.repository.FacilityRepository;
import dev.ngb.domain.institution.repository.InstitutionRepository;
import dev.ngb.system.institution.application.institution.use_case.create_institution.dto.CreateFacilityRequest;
import dev.ngb.system.institution.application.institution.use_case.create_institution.dto.CreateInstitutionRequest;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateInstitutionUseCase implements UseCaseService {

    private final InstitutionRepository institutionRepository;
    private final FacilityRepository facilityRepository;

    public void execute(CreateInstitutionRequest request) {
        if (institutionRepository.existsByCode(request.code())) {
            throw InstitutionError.INSTITUTION_CODE_ALREADY_EXISTS.exception();
        }

        Set<String> codes = new HashSet<>();
        for (CreateFacilityRequest facility : request.facilities()) {
            if (!codes.add(facility.facilityCode())) {
                throw InstitutionError.DUPLICATE_FACILITY_CODE.exception();
            }
        }

        Institution institution = Institution.create(
                request.code(),
                request.name(),
                request.timezone()
        );
        institution = institutionRepository.save(institution);

        UUID institutionId = institution.getId();
        String institutionCode = institution.getCode();

        List<Facility> facilities = request.facilities().stream()
                .map(f -> Facility.create(
                        institutionId,
                        f.facilityCode(),
                        institutionCode,
                        f.name(),
                        f.address(),
                        f.timezone()
                ))
                .toList();
        facilityRepository.saveAll(facilities);
    }
}
