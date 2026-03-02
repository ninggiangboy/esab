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
@Table("academy_term")
public class AcademyTermEntity extends SoftDeleteAuditableEntity<UUID> {

    private UUID facilityId;

    private String code;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer academicYear;

    private Integer semesterInYear;

    private String status;
}
