package dev.ngb.domain.institution.model;

import dev.ngb.domain.AuditableDomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Facility extends AuditableDomainEntity<UUID> {

    private UUID institutionId;
    private String facilityCode;
    private String code; // unique within institution
    private String name;
    private FacilityStatus status;
    private String address;
    private String timezone;

    private Facility() {
    }

    public static Facility create(
            UUID institutionId,
            String facilityCode,
            String institutionCode,
            String name,
            String address,
            String timezone) {
        Facility entity = new Facility();
        entity.institutionId = institutionId;
        entity.facilityCode = facilityCode;
        entity.code = institutionCode + "_" + facilityCode;
        entity.name = name;
        entity.status = FacilityStatus.ACTIVE;
        entity.address = address;
        entity.timezone = timezone;
        return entity;
    }

    public void update(String name, String address, String timezone, FacilityStatus status) {
        this.name = name;
        this.address = address;
        this.timezone = timezone;
        this.status = status;
    }

    public static Facility reconstruct(
            UUID id,
            UUID institutionId,
            String facilityCode,
            String code,
            String name,
            FacilityStatus status,
            String address,
            String timezone,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        Facility entity = new Facility();
        entity.id = id;
        entity.institutionId = institutionId;
        entity.facilityCode = facilityCode;
        entity.code = code;
        entity.name = name;
        entity.status = status;
        entity.address = address;
        entity.timezone = timezone;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
