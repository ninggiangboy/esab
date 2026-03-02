package dev.ngb.institution.catalog.application.course_master.use_case.create_course_master.dto;

import java.util.List;

public record CreateCourseMasterRequest(
        String code,
        String name,
        String description,
        Integer credit,
        String syllabus,
        String learningOutcome,
        String oldCourseCode,
        List<String> prerequisiteCourseCodes
) {
}
