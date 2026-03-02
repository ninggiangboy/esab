package dev.ngb.institution.catalog.web;

import dev.ngb.institution.catalog.application.course_master.use_case.create_course_master.dto.CreateCourseMasterRequest;
import dev.ngb.institution.catalog.application.course_master.use_case.update_course_master.dto.UpdateCourseMasterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/course-masters")
public interface CourseMasterApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody CreateCourseMasterRequest request);

    @PutMapping("/{id}")
    void update(@PathVariable UUID id, @RequestBody UpdateCourseMasterRequest request);
}
