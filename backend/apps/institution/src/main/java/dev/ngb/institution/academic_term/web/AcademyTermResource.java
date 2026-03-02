package dev.ngb.institution.academic_term.web;

import dev.ngb.institution.academic_term.application.academy_term.use_case.create_academy_term.CreateAcademyTermUseCase;
import dev.ngb.institution.academic_term.application.academy_term.use_case.create_academy_term.dto.CreateAcademyTermRequest;
import dev.ngb.institution.academic_term.application.academy_term.use_case.update_academy_term.UpdateAcademyTermUseCase;
import dev.ngb.institution.academic_term.application.academy_term.use_case.update_academy_term.dto.UpdateAcademyTermRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AcademyTermResource implements AcademyTermApi {

    private final CreateAcademyTermUseCase createAcademyTermUseCase;
    private final UpdateAcademyTermUseCase updateAcademyTermUseCase;

    @Override
    public void create(CreateAcademyTermRequest request) {
        createAcademyTermUseCase.execute(request);
    }

    @Override
    public void update(UUID id, UpdateAcademyTermRequest request) {
        updateAcademyTermUseCase.execute(id, request);
    }
}
