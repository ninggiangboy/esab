package dev.ngb.institution.catalog.application.course_master.service;

import dev.ngb.domain.ApplicationService;
import dev.ngb.domain.catalog.error.CourseMasterError;
import dev.ngb.domain.catalog.model.CourseMaster;
import dev.ngb.domain.catalog.repository.CourseMasterRepository;
import dev.ngb.domain.catalog.repository.CoursePrerequisiteRepository;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class CourseMasterValidator implements ApplicationService {

    private final CourseMasterRepository courseMasterRepository;
    private final CoursePrerequisiteRepository coursePrerequisiteRepository;

    public void validateOldCourseCode(String courseCode, String oldCourseCode) {
        if (oldCourseCode == null) {
            return;
        }

        if (courseCode.equals(oldCourseCode)) {
            throw CourseMasterError.CIRCULAR_OLD_COURSE.exception();
        }

        Set<String> visited = new HashSet<>();
        visited.add(courseCode);

        String current = oldCourseCode;
        while (current != null) {
            if (!visited.add(current)) {
                throw CourseMasterError.CIRCULAR_OLD_COURSE.exception();
            }
            Optional<CourseMaster> found = courseMasterRepository.findByCode(current);
            if (found.isEmpty()) {
                throw CourseMasterError.OLD_COURSE_NOT_FOUND.exception();
            }
            current = found.get().getOldCourseCode();
        }
    }

    public void validatePrerequisites(String courseCode, List<String> prerequisiteCourseCodes) {
        if (prerequisiteCourseCodes == null || prerequisiteCourseCodes.isEmpty()) {
            return;
        }

        if (prerequisiteCourseCodes.contains(courseCode)) {
            throw CourseMasterError.CIRCULAR_PREREQUISITE.exception();
        }

        if (!courseMasterRepository.existsAllByCodes(prerequisiteCourseCodes)) {
            throw CourseMasterError.PREREQUISITE_NOT_FOUND.exception();
        }

        if (coursePrerequisiteRepository.existsByAncestorCodeAndDescendantCodeIn(courseCode, prerequisiteCourseCodes)) {
            throw CourseMasterError.CIRCULAR_PREREQUISITE.exception();
        }
    }
}
