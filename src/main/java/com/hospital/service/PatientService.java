package com.hospital.service;

import com.hospital.dto.PatientRequest;
import com.hospital.dto.PatientResponse;
import com.hospital.exception.DuplicateResourceException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.model.Patient;
import com.hospital.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientResponse createPatient(PatientRequest request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Patient with email " + request.getEmail() + " already exists");
        }
        Patient patient = new Patient();
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setEmergencyContact(request.getEmergencyContact());
        patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        patient.setMedicalHistory(request.getMedicalHistory());
        patient.setActive(true);

        Patient saved = patientRepository.save(patient);
        log.info("Created patient with ID: {}", saved.getId());
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        return mapToResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findByActiveTrue().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> searchPatients(String name) {
        return patientRepository.searchByName(name).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = findById(id);
        if (!patient.getEmail().equals(request.getEmail()) && patientRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email " + request.getEmail() + " is already in use");
        }
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhone());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setAddress(request.getAddress());
        patient.setBloodGroup(request.getBloodGroup());
        patient.setEmergencyContact(request.getEmergencyContact());
        patient.setEmergencyContactPhone(request.getEmergencyContactPhone());
        patient.setMedicalHistory(request.getMedicalHistory());
        return mapToResponse(patientRepository.save(patient));
    }

    public void deletePatient(Long id) {
        Patient patient = findById(id);
        patient.setActive(false);
        patientRepository.save(patient);
        log.info("Soft-deleted patient with ID: {}", id);
    }

    private Patient findById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with ID: " + id));
    }

    private PatientResponse mapToResponse(Patient p) {
        PatientResponse r = new PatientResponse();
        r.setId(p.getId());
        r.setFirstName(p.getFirstName());
        r.setLastName(p.getLastName());
        r.setFullName(p.getFullName());
        r.setEmail(p.getEmail());
        r.setPhone(p.getPhone());
        r.setDateOfBirth(p.getDateOfBirth());
        r.setGender(p.getGender());
        r.setAddress(p.getAddress());
        r.setBloodGroup(p.getBloodGroup());
        r.setEmergencyContact(p.getEmergencyContact());
        r.setEmergencyContactPhone(p.getEmergencyContactPhone());
        r.setMedicalHistory(p.getMedicalHistory());
        r.setActive(p.isActive());
        r.setCreatedAt(p.getCreatedAt());
        return r;
    }
}
