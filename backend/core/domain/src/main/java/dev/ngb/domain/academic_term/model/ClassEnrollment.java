package dev.ngb.domain.academic_term.model;

import dev.ngb.domain.AuditableDomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ClassEnrollment extends AuditableDomainEntity<UUID> {

    private UUID academyTermId;
    private UUID classId;
    private String studentCode;
    private EnrollmentStatus status;
    private String grade;

    public static ClassEnrollment reconstruct(
            UUID id,
            UUID academyTermId,
            UUID classId,
            String studentCode,
            EnrollmentStatus status,
            String grade,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        ClassEnrollment entity = new ClassEnrollment();
        entity.id = id;
        entity.academyTermId = academyTermId;
        entity.classId = classId;
        entity.studentCode = studentCode;
        entity.status = status;
        entity.grade = grade;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
