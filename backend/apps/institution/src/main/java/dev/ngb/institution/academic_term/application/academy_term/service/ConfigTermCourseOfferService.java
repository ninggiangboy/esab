package dev.ngb.institution.academic_term.application.academy_term.service;

import dev.ngb.domain.ApplicationService;
import dev.ngb.domain.academic_term.error.CourseOfferError;
import dev.ngb.domain.academic_term.model.CourseOffer;
import dev.ngb.domain.academic_term.repository.CourseOfferRepository;
import dev.ngb.domain.catalog.repository.CourseMasterRepository;
import dev.ngb.institution.academic_term.application.academy_term.service.dto.CourseOfferItemRequest;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class ConfigTermCourseOfferService implements ApplicationService {

    private final CourseOfferRepository courseOfferRepository;
    private final CourseMasterRepository courseMasterRepository;

    public void setCourseOffersToTerm(UUID facilityId, UUID termId, List<CourseOfferItemRequest> items) {
        validateItems(items);

        List<CourseOffer> existing = courseOfferRepository.findByAcademyTermId(termId);
        if (!existing.isEmpty()) {
            courseOfferRepository.deleteAll(existing);
        }

        if (items != null && !items.isEmpty()) {
            List<CourseOffer> newOffers = items.stream()
                    .map(item -> CourseOffer.create(facilityId, termId, item.courseMasterId(), item.code(), item.maxStudents()))
                    .toList();
            courseOfferRepository.saveAll(newOffers);
        }
    }

    private void validateItems(List<CourseOfferItemRequest> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        Set<String> codes = new HashSet<>();
        Set<UUID> courseMasterIds = new HashSet<>();

        for (CourseOfferItemRequest item : items) {
            if (!codes.add(item.code())) {
                throw CourseOfferError.DUPLICATE_CODE_IN_REQUEST.exception();
            }
            courseMasterIds.add(item.courseMasterId());
        }

        long foundCount = courseMasterRepository.findByIds(new ArrayList<>(courseMasterIds)).size();
        if (foundCount != courseMasterIds.size()) {
            throw CourseOfferError.COURSE_MASTER_NOT_FOUND.exception();
        }
    }
}
