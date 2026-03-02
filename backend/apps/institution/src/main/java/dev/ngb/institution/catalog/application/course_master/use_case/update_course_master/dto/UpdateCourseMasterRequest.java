package dev.ngb.institution.catalog.application.course_master.use_case.update_course_master.dto;

import dev.ngb.domain.catalog.model.CourseMasterStatus;

import java.util.List;

public record UpdateCourseMasterRequest(
        String name,
        String description,
        Integer credit,
        String syllabus,
        String learningOutcome,
        String oldCourseCode,
        List<String> prerequisiteCourseCodes,
        CourseMasterStatus status
) {
}
