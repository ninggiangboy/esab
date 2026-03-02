package dev.ngb.domain.academic_term.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.academic_term.model.CourseOffer;

import java.util.List;
import java.util.UUID;

public interface CourseOfferRepository extends Repository<CourseOffer, UUID> {

    List<CourseOffer> findByAcademyTermId(UUID academyTermId);
}
