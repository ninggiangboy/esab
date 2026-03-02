package dev.ngb.system.institution.application.institution.use_case.update_institution;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.institution.error.InstitutionError;
import dev.ngb.domain.institution.model.Institution;
import dev.ngb.domain.institution.repository.InstitutionRepository;
import dev.ngb.system.institution.application.institution.use_case.update_institution.dto.UpdateInstitutionRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateInstitutionUseCase implements UseCaseService {

    private final InstitutionRepository institutionRepository;

    public void execute(UUID institutionId, UpdateInstitutionRequest request) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(InstitutionError.INSTITUTION_NOT_FOUND::exception);
        institution.update(request.name(), request.timezone(), request.status());
        institutionRepository.save(institution);
    }
}
