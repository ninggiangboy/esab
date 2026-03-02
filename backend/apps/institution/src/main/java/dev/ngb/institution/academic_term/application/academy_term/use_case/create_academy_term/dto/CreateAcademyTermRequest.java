package dev.ngb.institution.academic_term.application.academy_term.use_case.create_academy_term.dto;

import dev.ngb.institution.academic_term.application.academy_term.service.dto.CourseOfferItemRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateAcademyTermRequest(
        UUID facilityId,
        String code,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Integer academicYear,
        Integer semesterInYear,
        List<CourseOfferItemRequest> courseOffers
) {
}
