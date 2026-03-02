package dev.ngb.system.institution.application.institution.use_case.change_institution_status;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.institution.model.Institution;
import dev.ngb.domain.institution.error.InstitutionError;
import dev.ngb.domain.institution.model.InstitutionStatus;
import dev.ngb.domain.institution.repository.InstitutionRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ChangeInstitutionStatusUseCase implements UseCaseService {

    private final InstitutionRepository institutionRepository;

    public void executeChangeStatus(UUID institutionId, InstitutionStatus newStatus) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(InstitutionError.INSTITUTION_NOT_FOUND::exception);
        institution.changeStatus(newStatus);
        institutionRepository.save(institution);
    }

    public void executeActive(UUID institutionId) {
        Institution institution = institutionRepository.findById(institutionId)
                .orElseThrow(InstitutionError.INSTITUTION_NOT_FOUND::exception);
        institution.activate();
        institutionRepository.save(institution);
    }
}
