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
@Table("course_offer")
public class CourseOfferEntity extends SoftDeleteAuditableEntity<UUID> {

    private UUID facilityId;

    private UUID academyTermId;

    private UUID courseMasterId;

    private String code;

    private Integer maxStudents;
}
