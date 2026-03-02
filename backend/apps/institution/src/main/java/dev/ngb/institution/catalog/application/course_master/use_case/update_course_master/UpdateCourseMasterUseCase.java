package dev.ngb.institution.catalog.application.course_master.use_case.update_course_master;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.catalog.error.CourseMasterError;
import dev.ngb.domain.catalog.model.CourseMaster;
import dev.ngb.domain.catalog.repository.CourseMasterRepository;
import dev.ngb.institution.catalog.application.course_master.service.CourseMasterValidator;
import dev.ngb.institution.catalog.application.course_master.service.CoursePrerequisiteService;
import dev.ngb.institution.catalog.application.course_master.use_case.update_course_master.dto.UpdateCourseMasterRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateCourseMasterUseCase implements UseCaseService {

    private final CourseMasterRepository courseMasterRepository;
    private final CourseMasterValidator courseMasterValidator;
    private final CoursePrerequisiteService coursePrerequisiteService;

    public void execute(UUID id, UpdateCourseMasterRequest request) {
        CourseMaster courseMaster = courseMasterRepository.findById(id)
                .orElseThrow(CourseMasterError.COURSE_MASTER_NOT_FOUND::exception);

        courseMasterValidator.validateOldCourseCode(courseMaster.getCode(), request.oldCourseCode());
        courseMasterValidator.validatePrerequisites(courseMaster.getCode(), request.prerequisiteCourseCodes());

        courseMaster.update(
                request.name(),
                request.description(),
                request.credit(),
                request.syllabus(),
                request.learningOutcome(),
                request.oldCourseCode(),
                request.status()
        );
        courseMasterRepository.save(courseMaster);

        coursePrerequisiteService.replacePrerequisites(courseMaster.getCode(), request.prerequisiteCourseCodes());
    }
}
