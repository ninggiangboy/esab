package dev.ngb.institution.academic_term.web;

import dev.ngb.institution.academic_term.application.academy_term.use_case.create_academy_term.dto.CreateAcademyTermRequest;
import dev.ngb.institution.academic_term.application.academy_term.use_case.update_academy_term.dto.UpdateAcademyTermRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/academy-terms")
public interface AcademyTermApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody CreateAcademyTermRequest request);

    @PutMapping("/{id}")
    void update(@PathVariable UUID id, @RequestBody UpdateAcademyTermRequest request);
}
