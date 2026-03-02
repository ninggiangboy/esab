package dev.ngb.institution.academic_term.application.academy_term.use_case.update_academy_term;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.academic_term.error.AcademyTermError;
import dev.ngb.domain.academic_term.model.AcademyTerm;
import dev.ngb.domain.academic_term.repository.AcademyTermRepository;
import dev.ngb.institution.academic_term.application.academy_term.service.ConfigTermCourseOfferService;
import dev.ngb.institution.academic_term.application.academy_term.use_case.update_academy_term.dto.UpdateAcademyTermRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateAcademyTermUseCase implements UseCaseService {

    private final AcademyTermRepository academyTermRepository;
    private final ConfigTermCourseOfferService configTermCourseOfferService;

    public void execute(UUID id, UpdateAcademyTermRequest request) {
        AcademyTerm academyTerm = academyTermRepository.findById(id)
                .orElseThrow(AcademyTermError.ACADEMY_TERM_NOT_FOUND::exception);

        academyTerm.update(
                request.name(),
                request.startDate(),
                request.endDate(),
                request.academicYear(),
                request.semesterInYear(),
                request.status()
        );
        academyTermRepository.save(academyTerm);

        if (request.courseOffers() != null) {
            configTermCourseOfferService.setCourseOffersToTerm(
                    academyTerm.getFacilityId(),
                    id,
                    request.courseOffers()
            );
        }
    }
}
