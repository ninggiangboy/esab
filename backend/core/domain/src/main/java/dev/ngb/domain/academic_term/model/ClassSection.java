package dev.ngb.domain.academic_term.model;

import dev.ngb.domain.AuditableDomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ClassSection extends AuditableDomainEntity<UUID> {

    private UUID facilityId;
    private UUID academyTermId;
    private UUID courseOfferId;
    private String teacherCode;
    private String code;
    private String name;

    public static ClassSection reconstruct(
            UUID id,
            UUID facilityId,
            UUID academyTermId,
            UUID courseOfferId,
            String teacherCode,
            String code,
            String name,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        ClassSection entity = new ClassSection();
        entity.id = id;
        entity.facilityId = facilityId;
        entity.academyTermId = academyTermId;
        entity.courseOfferId = courseOfferId;
        entity.teacherCode = teacherCode;
        entity.code = code;
        entity.name = name;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
