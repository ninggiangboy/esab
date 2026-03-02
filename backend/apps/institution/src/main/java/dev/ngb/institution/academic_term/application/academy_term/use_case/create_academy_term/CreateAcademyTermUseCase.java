package dev.ngb.institution.academic_term.application.academy_term.use_case.create_academy_term;

import dev.ngb.application.UseCaseService;
import dev.ngb.domain.academic_term.error.AcademyTermError;
import dev.ngb.domain.academic_term.model.AcademyTerm;
import dev.ngb.domain.academic_term.repository.AcademyTermRepository;
import dev.ngb.institution.academic_term.application.academy_term.service.ConfigTermCourseOfferService;
import dev.ngb.institution.academic_term.application.academy_term.use_case.create_academy_term.dto.CreateAcademyTermRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAcademyTermUseCase implements UseCaseService {

    private final AcademyTermRepository academyTermRepository;
    private final ConfigTermCourseOfferService configTermCourseOfferService;

    public void execute(CreateAcademyTermRequest request) {
        if (academyTermRepository.existsByCodeAndFacilityId(request.code(), request.facilityId())) {
            throw AcademyTermError.ACADEMY_TERM_CODE_ALREADY_EXISTS.exception();
        }

        AcademyTerm academyTerm = AcademyTerm.create(
                request.facilityId(),
                request.code(),
                request.name(),
                request.startDate(),
                request.endDate(),
                request.academicYear(),
                request.semesterInYear()
        );
        academyTermRepository.save(academyTerm);

        if (request.courseOffers() != null && !request.courseOffers().isEmpty()) {
            configTermCourseOfferService.setCourseOffersToTerm(
                    academyTerm.getFacilityId(),
                    academyTerm.getId(),
                    request.courseOffers()
            );
        }
    }
}
