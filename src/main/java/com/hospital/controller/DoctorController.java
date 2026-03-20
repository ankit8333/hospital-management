package com.hospital.controller;

import com.hospital.dto.ApiResponse;
import com.hospital.dto.DoctorRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@Tag(name = "Doctors", description = "Doctor management")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @Operation(summary = "Add a new doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> create(@Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Doctor added successfully", doctorService.createDoctor(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get doctor by ID")
    public ResponseEntity<ApiResponse<DoctorResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Doctor found", doctorService.getDoctorById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Doctors retrieved", doctorService.getAllDoctors()));
    }

    @GetMapping("/available")
    @Operation(summary = "Get available doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAvailable() {
        return ResponseEntity.ok(ApiResponse.success("Available doctors", doctorService.getAvailableDoctors()));
    }

    @GetMapping("/specialization/{specialization}")
    @Operation(summary = "Get doctors by specialization")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(ApiResponse.success("Doctors found", doctorService.getDoctorsBySpecialization(specialization)));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get doctors by department")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(ApiResponse.success("Doctors found", doctorService.getDoctorsByDepartment(departmentId)));
    }

    @GetMapping("/search")
    @Operation(summary = "Search doctors by name")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> search(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success("Search results", doctorService.searchDoctors(name)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> update(@PathVariable Long id,
                                                               @Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Doctor updated", doctorService.updateDoctor(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Mark doctor as unavailable")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(ApiResponse.success("Doctor marked as unavailable", null));
    }
}
