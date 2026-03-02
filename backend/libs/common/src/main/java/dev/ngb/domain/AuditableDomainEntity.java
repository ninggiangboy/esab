package dev.ngb.domain;

import lombok.Getter;

import java.time.Instant;

@Getter
public abstract class AuditableDomainEntity<ID> extends DomainEntity<ID> {

    protected String createdBy;
    protected Instant createdAt;
    protected String updatedBy;
    protected Instant updatedAt;
}
