package dev.ngb.domain.catalog.error;

import dev.ngb.domain.DomainError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseMasterError implements DomainError {

    COURSE_MASTER_NOT_FOUND("Course master not found"),
    COURSE_MASTER_CODE_ALREADY_EXISTS("Course master code already exists"),
    OLD_COURSE_NOT_FOUND("Old course master not found"),
    CIRCULAR_OLD_COURSE("Circular old course dependency detected"),
    PREREQUISITE_NOT_FOUND("Prerequisite course master not found"),
    CIRCULAR_PREREQUISITE("Circular prerequisite dependency detected"),
    INVALID_STATUS_TRANSITION("Invalid course master status transition");

    private final String message;
}
