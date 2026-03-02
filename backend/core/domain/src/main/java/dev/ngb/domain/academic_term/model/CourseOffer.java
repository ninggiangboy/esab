package dev.ngb.domain.academic_term.model;

import dev.ngb.domain.AuditableDomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class CourseOffer extends AuditableDomainEntity<UUID> {

    private UUID facilityId;
    private UUID academyTermId;
    private UUID courseMasterId;
    private String code;
    private Integer maxStudents;

    private CourseOffer() {
    }

    public static CourseOffer create(
            UUID facilityId,
            UUID academyTermId,
            UUID courseMasterId,
            String code,
            Integer maxStudents
    ) {
        CourseOffer entity = new CourseOffer();
        entity.facilityId = facilityId;
        entity.academyTermId = academyTermId;
        entity.courseMasterId = courseMasterId;
        entity.code = code;
        entity.maxStudents = maxStudents;
        return entity;
    }

    public static CourseOffer reconstruct(
            UUID id,
            UUID facilityId,
            UUID academyTermId,
            UUID courseMasterId,
            String code,
            Integer maxStudents,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        CourseOffer entity = new CourseOffer();
        entity.id = id;
        entity.facilityId = facilityId;
        entity.academyTermId = academyTermId;
        entity.courseMasterId = courseMasterId;
        entity.code = code;
        entity.maxStudents = maxStudents;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
