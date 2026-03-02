package dev.ngb.domain.academic_term.model;

import dev.ngb.domain.AuditableDomainEntity;
import dev.ngb.domain.academic_term.error.AcademyTermError;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class AcademyTerm extends AuditableDomainEntity<UUID> {

    private UUID facilityId;
    private String code;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer academicYear;
    private Integer semesterInYear;
    private AcademyTermStatus status;

    private AcademyTerm() {
    }

    public static AcademyTerm create(
            UUID facilityId,
            String code,
            String name,
            LocalDate startDate,
            LocalDate endDate,
            Integer academicYear,
            Integer semesterInYear
    ) {
        AcademyTerm entity = new AcademyTerm();
        entity.facilityId = facilityId;
        entity.code = code;
        entity.name = name;
        entity.startDate = startDate;
        entity.endDate = endDate;
        entity.academicYear = academicYear;
        entity.semesterInYear = semesterInYear;
        entity.status = AcademyTermStatus.PLANNED;
        return entity;
    }

    public void update(
            String name,
            LocalDate startDate,
            LocalDate endDate,
            Integer academicYear,
            Integer semesterInYear,
            AcademyTermStatus status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.academicYear = academicYear;
        this.semesterInYear = semesterInYear;
        changeStatus(status);
    }

    public void changeStatus(AcademyTermStatus newStatus) {
        if (this.status == newStatus) {
            return;
        }
        boolean valid = switch (this.status) {
            case PLANNED -> newStatus == AcademyTermStatus.IN_PROGRESS || newStatus == AcademyTermStatus.CANCELLED;
            case IN_PROGRESS -> newStatus == AcademyTermStatus.COMPLETED || newStatus == AcademyTermStatus.CANCELLED;
            case COMPLETED, CANCELLED -> false;
        };
        if (!valid) {
            throw AcademyTermError.INVALID_STATUS_TRANSITION.exception();
        }
        this.status = newStatus;
    }

    public static AcademyTerm reconstruct(
            UUID id,
            UUID facilityId,
            String code,
            String name,
            LocalDate startDate,
            LocalDate endDate,
            Integer academicYear,
            Integer semester,
            AcademyTermStatus status,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        AcademyTerm entity = new AcademyTerm();
        entity.id = id;
        entity.facilityId = facilityId;
        entity.code = code;
        entity.name = name;
        entity.startDate = startDate;
        entity.endDate = endDate;
        entity.academicYear = academicYear;
        entity.semesterInYear = semester;
        entity.status = status;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
