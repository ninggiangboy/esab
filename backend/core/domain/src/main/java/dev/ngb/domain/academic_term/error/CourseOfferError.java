package dev.ngb.domain.academic_term.error;

import dev.ngb.domain.DomainError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseOfferError implements DomainError {

    COURSE_MASTER_NOT_FOUND("Course master not found"),
    DUPLICATE_CODE_IN_REQUEST("Duplicate course offer code in request");

    private final String message;
}
