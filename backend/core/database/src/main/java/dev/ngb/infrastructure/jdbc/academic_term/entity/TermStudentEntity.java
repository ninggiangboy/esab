package dev.ngb.infrastructure.jdbc.academic_term.entity;

import dev.ngb.infrastructure.jdbc.base.SoftDeleteAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("term_student")
public class TermStudentEntity extends SoftDeleteAuditableEntity<UUID> {

    private UUID facilityId;

    private UUID academyTermId;

    private String studentCode;

    private String fullName;

    private LocalDate dob;

    private String email;

    private String status;
}
