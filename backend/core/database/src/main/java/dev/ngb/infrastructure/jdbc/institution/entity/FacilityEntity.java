package dev.ngb.infrastructure.jdbc.institution.entity;

import dev.ngb.infrastructure.jdbc.base.AuditableEntity;
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
@Table("facility")
public class FacilityEntity extends AuditableEntity<UUID> {

    private UUID institutionId;

    private String facilityCode;

    private String code;

    private String name;

    private String status;

    private String address;

    private String timezone;
}
