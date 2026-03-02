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
@Table("class_enrollment")
public class ClassEnrollmentEntity extends SoftDeleteAuditableEntity<UUID> {

    private UUID academyTermId;

    private UUID classId;

    private String studentCode;

    private String status;

    private String grade;
}
