package dev.ngb.infrastructure.jdbc.academic_term.entity;

import dev.ngb.infrastructure.jdbc.base.SoftDeleteAuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table("class_schedule")
public class ClassScheduleEntity extends SoftDeleteAuditableEntity<UUID> {

    private UUID classId;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private String room;
}
