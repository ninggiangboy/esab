package dev.ngb.domain.academic_term.model;

import dev.ngb.domain.AuditableDomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class TermTeacher extends AuditableDomainEntity<UUID> {

    private UUID facilityId;
    private UUID academyTermId;
    private String teacherCode;
    private String fullName;
    private String email;
    private TermTeacherStatus status;

    public static TermTeacher reconstruct(
            UUID id,
            UUID facilityId,
            UUID academyTermId,
            String teacherCode,
            String fullName,
            String email,
            TermTeacherStatus status,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        TermTeacher entity = new TermTeacher();
        entity.id = id;
        entity.facilityId = facilityId;
        entity.academyTermId = academyTermId;
        entity.teacherCode = teacherCode;
        entity.fullName = fullName;
        entity.email = email;
        entity.status = status;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
