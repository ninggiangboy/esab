package dev.ngb.institution.catalog.web;

import dev.ngb.institution.catalog.application.course_master.use_case.create_course_master.CreateCourseMasterUseCase;
import dev.ngb.institution.catalog.application.course_master.use_case.create_course_master.dto.CreateCourseMasterRequest;
import dev.ngb.institution.catalog.application.course_master.use_case.update_course_master.UpdateCourseMasterUseCase;
import dev.ngb.institution.catalog.application.course_master.use_case.update_course_master.dto.UpdateCourseMasterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CourseMasterResource implements CourseMasterApi {

    private final CreateCourseMasterUseCase createCourseMasterUseCase;
    private final UpdateCourseMasterUseCase updateCourseMasterUseCase;

    @Override
    public void create(CreateCourseMasterRequest request) {
        createCourseMasterUseCase.execute(request);
    }

    @Override
    public void update(UUID id, UpdateCourseMasterRequest request) {
        updateCourseMasterUseCase.execute(id, request);
    }
}
