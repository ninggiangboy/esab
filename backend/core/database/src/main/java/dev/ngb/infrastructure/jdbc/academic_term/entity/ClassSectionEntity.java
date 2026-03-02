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
@Table("class_section")
public class ClassSectionEntity extends SoftDeleteAuditableEntity<UUID> {

    private UUID facilityId;

    private UUID academyTermId;

    private UUID courseOfferId;

    private String teacherCode;

    private String code;

    private String name;

}
