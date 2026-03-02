package dev.ngb.domain.academic_term.model;

import dev.ngb.domain.AuditableDomainEntity;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Getter
public class ClassSchedule extends AuditableDomainEntity<UUID> {

    private UUID classId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;

    public static ClassSchedule reconstruct(
            UUID id,
            UUID classId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            String room,
            String createdBy,
            Instant createdAt,
            String updatedBy,
            Instant updatedAt
    ) {
        ClassSchedule entity = new ClassSchedule();
        entity.id = id;
        entity.classId = classId;
        entity.dayOfWeek = dayOfWeek;
        entity.startTime = startTime;
        entity.endTime = endTime;
        entity.room = room;
        entity.createdBy = createdBy;
        entity.createdAt = createdAt;
        entity.updatedBy = updatedBy;
        entity.updatedAt = updatedAt;
        return entity;
    }
}
