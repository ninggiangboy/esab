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
@Table("course_master")
public class CourseMasterEntity extends SoftDeleteAuditableEntity<UUID> {

    private String code;

    private String name;

    private String description;

    private Integer credit;

    private Integer version;

    private String syllabus;

    private String learningOutcome;

    private String oldCourseCode;

    private String status;
}
