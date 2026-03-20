package com.hospital.controller;

import com.hospital.dto.ApiResponse;
import com.hospital.dto.PatientRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "Patient management")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Register a new patient")
    public ResponseEntity<ApiResponse<PatientResponse>> create(@Valid @RequestBody PatientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Patient registered successfully", patientService.createPatient(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get patient by ID")
    public ResponseEntity<ApiResponse<PatientResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Patient found", patientService.getPatientById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all active patients")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Patients retrieved", patientService.getAllPatients()));
    }

    @GetMapping("/search")
    @Operation(summary = "Search patients by name")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> search(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success("Search results", patientService.searchPatients(name)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update patient")
    public ResponseEntity<ApiResponse<PatientResponse>> update(@PathVariable Long id,
                                                                @Valid @RequestBody PatientRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Patient updated", patientService.updatePatient(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate patient")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(ApiResponse.success("Patient deactivated", null));
    }
}
