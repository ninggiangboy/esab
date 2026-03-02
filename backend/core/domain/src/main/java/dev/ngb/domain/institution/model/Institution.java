package dev.ngb.domain.institution.model;

import dev.ngb.domain.AuditableDomainEntity;
import dev.ngb.domain.institution.error.InstitutionError;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
public class Institution extends AuditableDomainEntity<UUID> {

    private String code;
    private String name;
    private InstitutionStatus status;
    private String timezone;

    private Institution() {
    }

    public static Institution create(String code, String name, String timezone) {
        Institution entity = new Institution();
        entity.code = code;
        entity.name = name;
        entity.status = InstitutionStatus.SETUP;
        entity.timezone = timezone;
        return entity;
    }

    public void activate() {
        if (this.status != InstitutionStatus.SETUP) {
            throw InstitutionError.NOT_IN_SETUP_STATUS.exception();
        }
        this.status = InstitutionStatus.ACTIVE;
    }

    public void update(String name, String timezone, InstitutionStatus status) {
        this.name = name;
        this.timezone = timezone;
        changeStatus(status);
    }

    public void changeStatus(InstitutionStatus newStatus) {
        if (newStatus == InstitutionStatus.SETUP) {
            throw InstitutionError.CANNOT_TRANSITION_TO_SETUP.exception();
        }
        if (this.status == InstitutionStatus.SETUP) {
            throw InstitutionError.INVALID_STATUS_TRANSITION.exception();
        }
        this.status = newStatus;
    }

    public static Institution reconstruct(
            UUID id,
            String code,
            String name,
            InstitutionStatus status,
            String timezone,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        Institution entity = new Institution();
        entity.id = id;
        entity.code = code;
        entity.name = name;
        entity.status = status;
        entity.timezone = timezone;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
