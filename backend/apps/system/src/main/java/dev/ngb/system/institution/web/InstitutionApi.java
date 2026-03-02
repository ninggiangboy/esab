package dev.ngb.system.institution.web;

import dev.ngb.system.institution.application.institution.use_case.change_institution_status.dto.ChangeInstitutionStatusRequest;
import dev.ngb.system.institution.application.institution.use_case.create_institution.dto.CreateInstitutionRequest;
import dev.ngb.system.institution.application.institution.use_case.update_institution.dto.UpdateInstitutionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/institutions")
public interface InstitutionApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody CreateInstitutionRequest request);

    @PutMapping("/{id}")
    void update(@PathVariable UUID id, @RequestBody UpdateInstitutionRequest request);

    @PatchMapping("/{id}/activate")
    void activate(@PathVariable UUID id);

    @PatchMapping("/{id}/status")
    void changeStatus(@PathVariable UUID id, @RequestBody ChangeInstitutionStatusRequest request);
}
