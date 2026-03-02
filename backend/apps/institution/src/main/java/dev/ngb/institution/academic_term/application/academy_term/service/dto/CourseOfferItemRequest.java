package dev.ngb.institution.academic_term.application.academy_term.service.dto;

import java.util.UUID;

public record CourseOfferItemRequest(
        UUID courseMasterId,
        String code,
        Integer maxStudents
) {
}
