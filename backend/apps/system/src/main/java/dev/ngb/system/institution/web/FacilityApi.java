package dev.ngb.system.institution.web;

import dev.ngb.system.institution.application.facility.use_case.add_facility.dto.AddFacilityRequest;
import dev.ngb.system.institution.application.facility.use_case.update_facility.dto.UpdateFacilityRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/facilities")
public interface FacilityApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void add(@RequestBody AddFacilityRequest request);

    @PatchMapping("/{id}")
    void update(@PathVariable UUID id, @RequestBody UpdateFacilityRequest request);
}
