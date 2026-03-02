package dev.ngb.infrastructure.jdbc.academic_term.entity;

import dev.ngb.infrastructure.jdbc.base.SoftDeleteAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("term_teacher")
public class TermTeacherEntity extends SoftDeleteAuditableEntity<UUID> {

    private UUID facilityId;

    private UUID academyTermId;

    private String teacherCode;

    private String fullName;

    private String email;

    private String status;
}
