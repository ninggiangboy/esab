package dev.ngb.infrastructure.jdbc.academic_term.repository;

import dev.ngb.domain.academic_term.model.ClassSchedule;
import dev.ngb.domain.academic_term.repository.ClassScheduleRepository;
import dev.ngb.infrastructure.jdbc.academic_term.entity.ClassScheduleEntity;
import dev.ngb.infrastructure.jdbc.base.JdbcMetadataHelper;
import dev.ngb.infrastructure.jdbc.base.JdbcRepository;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public class ClassScheduleJdbcRepository
        extends JdbcRepository<ClassSchedule, ClassScheduleEntity, UUID>
        implements ClassScheduleRepository {

    public ClassScheduleJdbcRepository(
            JdbcClient jdbcClient,
            JdbcTemplate jdbcTemplate,
            JdbcAggregateTemplate jdbcAggregate,
            JdbcMetadataHelper jdbcMetadataHelper
    ) {
        super(ClassScheduleEntity.class, jdbcClient, jdbcTemplate, jdbcAggregate, jdbcMetadataHelper);
    }

    @Override
    protected ClassSchedule mapToDomain(ClassScheduleEntity entity) {
        return ClassSchedule.reconstruct(
                entity.getId(),
                entity.getClassId(),
                entity.getDayOfWeek(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getRoom(),
                entity.getCreatedBy(),
                entity.getCreatedAt(),
                entity.getUpdatedBy(),
                entity.getUpdatedAt()
        );
    }

    @Override
    protected ClassScheduleEntity mapToJdbc(ClassSchedule domain) {
        return ClassScheduleEntity.builder()
                .id(domain.getId())
                .classId(domain.getClassId())
                .dayOfWeek(domain.getDayOfWeek())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .room(domain.getRoom())
                .createdBy(domain.getCreatedBy())
                .createdAt(domain.getCreatedAt())
                .updatedBy(domain.getUpdatedBy())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
