package dev.ngb.domain.catalog.model;

import dev.ngb.domain.AuditableDomainEntity;
import dev.ngb.domain.catalog.error.CourseMasterError;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class CourseMaster extends AuditableDomainEntity<UUID> {

    private String code;
    private String name;
    private String description;
    private Integer credit;
    private Integer version;
    private String syllabus;
    private String learningOutcome;
    private String oldCourseCode;
    private CourseMasterStatus status;

    private CourseMaster() {
    }

    public static CourseMaster create(
            String code,
            String name,
            String description,
            Integer credit,
            String syllabus,
            String learningOutcome,
            String oldCourseCode
    ) {
        CourseMaster entity = new CourseMaster();
        entity.code = code;
        entity.name = name;
        entity.description = description;
        entity.credit = credit;
        entity.version = 1;
        entity.syllabus = syllabus;
        entity.learningOutcome = learningOutcome;
        entity.oldCourseCode = oldCourseCode;
        entity.status = CourseMasterStatus.DRAFT;
        return entity;
    }

    public void update(
            String name,
            String description,
            Integer credit,
            String syllabus,
            String learningOutcome,
            String oldCourseCode,
            CourseMasterStatus status
    ) {
        this.name = name;
        this.description = description;
        this.credit = credit;
        this.syllabus = syllabus;
        this.learningOutcome = learningOutcome;
        this.oldCourseCode = oldCourseCode;
        changeStatus(status);
    }

    public void changeStatus(CourseMasterStatus newStatus) {
        if (this.status == newStatus) {
            return;
        }
        boolean valid = switch (this.status) {
            case DRAFT -> newStatus == CourseMasterStatus.ACTIVE;
            case ACTIVE -> newStatus == CourseMasterStatus.INACTIVE || newStatus == CourseMasterStatus.DEPRECATED;
            case INACTIVE -> newStatus == CourseMasterStatus.ACTIVE || newStatus == CourseMasterStatus.DEPRECATED;
            case DEPRECATED -> false;
        };
        if (!valid) {
            throw CourseMasterError.INVALID_STATUS_TRANSITION.exception();
        }
        this.status = newStatus;
    }

    public static CourseMaster reconstruct(
            UUID id,
            String code,
            String name,
            String description,
            Integer credit,
            Integer version,
            String syllabus,
            String learningOutcome,
            String oldCourseCode,
            CourseMasterStatus status,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        CourseMaster entity = new CourseMaster();
        entity.id = id;
        entity.code = code;
        entity.name = name;
        entity.description = description;
        entity.credit = credit;
        entity.version = version;
        entity.syllabus = syllabus;
        entity.learningOutcome = learningOutcome;
        entity.oldCourseCode = oldCourseCode;
        entity.status = status;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
