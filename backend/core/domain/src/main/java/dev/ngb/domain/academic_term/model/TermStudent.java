package dev.ngb.domain.academic_term.model;

import dev.ngb.domain.AuditableDomainEntity;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class TermStudent extends AuditableDomainEntity<UUID> {

    private UUID facilityId;
    private UUID academyTermId;
    private String studentCode;
    private String fullName;
    private LocalDate dob;
    private String email;
    private TermStudentStatus status;

    public static TermStudent reconstruct(
            UUID id,
            UUID facilityId,
            UUID academyTermId,
            String studentCode,
            String fullName,
            LocalDate dob,
            String email,
            TermStudentStatus status,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        TermStudent entity = new TermStudent();
        entity.id = id;
        entity.facilityId = facilityId;
        entity.academyTermId = academyTermId;
        entity.studentCode = studentCode;
        entity.fullName = fullName;
        entity.dob = dob;
        entity.email = email;
        entity.status = status;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
