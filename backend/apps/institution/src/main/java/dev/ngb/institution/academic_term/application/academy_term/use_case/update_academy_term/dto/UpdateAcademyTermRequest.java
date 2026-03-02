package dev.ngb.institution.academic_term.application.academy_term.use_case.update_academy_term.dto;

import dev.ngb.domain.academic_term.model.AcademyTermStatus;
import dev.ngb.institution.academic_term.application.academy_term.service.dto.CourseOfferItemRequest;

import java.time.LocalDate;
import java.util.List;

public record UpdateAcademyTermRequest(
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Integer academicYear,
        Integer semesterInYear,
        AcademyTermStatus status,
        List<CourseOfferItemRequest> courseOffers
) {
}
