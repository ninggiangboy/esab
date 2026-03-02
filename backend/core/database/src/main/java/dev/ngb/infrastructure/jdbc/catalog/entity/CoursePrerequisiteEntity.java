package dev.ngb.infrastructure.jdbc.catalog.entity;

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
@Table("course_prerequisite")
public class CoursePrerequisiteEntity extends SoftDeleteAuditableEntity<UUID> {
    private String ancestorCode;
    private String descendantCode;
    private Integer depth;
}
