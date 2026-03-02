package dev.ngb.domain.catalog.model;

import dev.ngb.domain.AuditableDomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class CoursePrerequisite extends AuditableDomainEntity<UUID> {

    private String ancestorCode;
    private String descendantCode;
    private Integer depth;

    private CoursePrerequisite() {
    }

    public static CoursePrerequisite create(String ancestorCode, String descendantCode, Integer depth) {
        CoursePrerequisite entity = new CoursePrerequisite();
        entity.ancestorCode = ancestorCode;
        entity.descendantCode = descendantCode;
        entity.depth = depth;
        return entity;
    }

    public static CoursePrerequisite reconstruct(
            UUID id,
            String ancestorCode,
            String descendantCode,
            Integer depth,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        CoursePrerequisite entity = new CoursePrerequisite();
        entity.id = id;
        entity.ancestorCode = ancestorCode;
        entity.descendantCode = descendantCode;
        entity.depth = depth;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
