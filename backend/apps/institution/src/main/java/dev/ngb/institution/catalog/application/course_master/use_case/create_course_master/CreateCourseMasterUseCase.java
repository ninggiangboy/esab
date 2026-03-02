package dev.ngb.institution.catalog.application.course_master.use_case.create_course_master;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.catalog.error.CourseMasterError;
import dev.ngb.domain.catalog.model.CourseMaster;
import dev.ngb.domain.catalog.repository.CourseMasterRepository;
import dev.ngb.institution.catalog.application.course_master.service.CourseMasterValidator;
import dev.ngb.institution.catalog.application.course_master.service.CoursePrerequisiteService;
import dev.ngb.institution.catalog.application.course_master.use_case.create_course_master.dto.CreateCourseMasterRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateCourseMasterUseCase implements UseCaseService {

    private final CourseMasterRepository courseMasterRepository;
    private final CourseMasterValidator courseMasterValidator;
    private final CoursePrerequisiteService coursePrerequisiteService;

    public void execute(CreateCourseMasterRequest request) {
        if (courseMasterRepository.existsByCode(request.code())) {
            throw CourseMasterError.COURSE_MASTER_CODE_ALREADY_EXISTS.exception();
        }

        courseMasterValidator.validateOldCourseCode(request.code(), request.oldCourseCode());
        courseMasterValidator.validatePrerequisites(request.code(), request.prerequisiteCourseCodes());

        CourseMaster courseMaster = CourseMaster.create(
                request.code(),
                request.name(),
                request.description(),
                request.credit(),
                request.syllabus(),
                request.learningOutcome(),
                request.oldCourseCode()
        );
        courseMasterRepository.save(courseMaster);

        coursePrerequisiteService.replacePrerequisites(request.code(), request.prerequisiteCourseCodes());
    }
}
