package dev.ngb.domain.academic_term.error;

import dev.ngb.domain.DomainError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AcademyTermError implements DomainError {

    ACADEMY_TERM_NOT_FOUND("Academy term not found"),
    ACADEMY_TERM_CODE_ALREADY_EXISTS("Academy term code already exists for this facility"),
    INVALID_STATUS_TRANSITION("Invalid academy term status transition");

    private final String message;
}
