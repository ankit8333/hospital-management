package com.hospital.controller;

import com.hospital.dto.ApiResponse;
import com.hospital.dto.MedicalRecordRequest;
import com.hospital.dto.MedicalRecordResponse;
import com.hospital.service.MedicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
@Tag(name = "Medical Records", description = "Medical record management")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    @Operation(summary = "Create a medical record")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> create(@Valid @RequestBody MedicalRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Medical record created", medicalRecordService.createRecord(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get medical record by ID")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Record found", medicalRecordService.getRecordById(id)));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get all records for a patient")
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(ApiResponse.success("Records found", medicalRecordService.getRecordsByPatient(patientId)));
    }

    @GetMapping("/doctor/{doctorId}")
    @Operation(summary = "Get all records by doctor")
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> getByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(ApiResponse.success("Records found", medicalRecordService.getRecordsByDoctor(doctorId)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a medical record")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> update(@PathVariable Long id,
                                                                      @Valid @RequestBody MedicalRecordRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Record updated", medicalRecordService.updateRecord(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a medical record")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        medicalRecordService.deleteRecord(id);
        return ResponseEntity.ok(ApiResponse.success("Record deleted", null));
    }
}
