package dev.ngb.system.institution.web;

import dev.ngb.system.institution.application.institution.use_case.change_institution_status.ChangeInstitutionStatusUseCase;
import dev.ngb.system.institution.application.institution.use_case.change_institution_status.dto.ChangeInstitutionStatusRequest;
import dev.ngb.system.institution.application.institution.use_case.create_institution.CreateInstitutionUseCase;
import dev.ngb.system.institution.application.institution.use_case.create_institution.dto.CreateInstitutionRequest;
import dev.ngb.system.institution.application.institution.use_case.update_institution.UpdateInstitutionUseCase;
import dev.ngb.system.institution.application.institution.use_case.update_institution.dto.UpdateInstitutionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class InstitutionResource implements InstitutionApi {

    private final CreateInstitutionUseCase createInstitutionUseCase;
    private final UpdateInstitutionUseCase updateInstitutionUseCase;
    private final ChangeInstitutionStatusUseCase changeInstitutionStatusUseCase;

    @Override
    public void create(CreateInstitutionRequest request) {
        createInstitutionUseCase.execute(request);
    }

    @Override
    public void update(UUID id, UpdateInstitutionRequest request) {
        updateInstitutionUseCase.execute(id, request);
    }

    @Override
    public void activate(UUID id) {
        changeInstitutionStatusUseCase.executeActive(id);
    }

    @Override
    public void changeStatus(UUID id, ChangeInstitutionStatusRequest request) {
        changeInstitutionStatusUseCase.executeChangeStatus(id, request.status());
    }
}
