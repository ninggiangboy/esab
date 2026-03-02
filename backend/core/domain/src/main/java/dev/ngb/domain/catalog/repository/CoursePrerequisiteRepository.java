package dev.ngb.domain.catalog.repository;

import dev.ngb.domain.Repository;
import dev.ngb.domain.catalog.model.CoursePrerequisite;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CoursePrerequisiteRepository extends Repository<CoursePrerequisite, UUID> {

    List<CoursePrerequisite> findByDescendantCode(String descendantCode);

    List<CoursePrerequisite> findByDescendantCodeIn(Collection<String> descendantCodes);

    List<CoursePrerequisite> findByAncestorCode(String ancestorCode);

    boolean existsByAncestorCodeAndDescendantCodeIn(String ancestorCode, Collection<String> descendantCodes);
}
