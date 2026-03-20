package com.hospital.service;

import com.hospital.dto.DoctorRequest;
import com.hospital.dto.DoctorResponse;
import com.hospital.exception.DuplicateResourceException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.model.Department;
import com.hospital.model.Doctor;
import com.hospital.repository.DepartmentRepository;
import com.hospital.repository.DoctorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DoctorService {

    private static final Logger log = LoggerFactory.getLogger(DoctorService.class);
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    public DoctorService(DoctorRepository doctorRepository, DepartmentRepository departmentRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
    }

    public DoctorResponse createDoctor(DoctorRequest request) {
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Doctor with email " + request.getEmail() + " already exists");
        }
        if (request.getLicenseNumber() != null && !request.getLicenseNumber().isEmpty()
                && doctorRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new DuplicateResourceException("License number already registered");
        }

        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + request.getDepartmentId()));
        }

        Doctor doctor = new Doctor();
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setAvailable(request.isAvailable());
        doctor.setDepartment(department);

        Doctor saved = doctorRepository.save(doctor);
        log.info("Created doctor with ID: {}", saved.getId());
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long id) {
        return mapToResponse(findDoctorById(id));
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> getAvailableDoctors() {
        return doctorRepository.findByAvailableTrue().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationIgnoreCase(specialization).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> getDoctorsByDepartment(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DoctorResponse> searchDoctors(String name) {
        return doctorRepository.searchByName(name).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public DoctorResponse updateDoctor(Long id, DoctorRequest request) {
        Doctor doctor = findDoctorById(id);
        if (!doctor.getEmail().equals(request.getEmail()) && doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email " + request.getEmail() + " is already in use");
        }

        Department department = null;
        if (request.getDepartmentId() != null) {
            department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        }

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhone());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setAvailable(request.isAvailable());
        doctor.setDepartment(department);

        return mapToResponse(doctorRepository.save(doctor));
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = findDoctorById(id);
        doctor.setAvailable(false);
        doctorRepository.save(doctor);
    }

    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
    }

    private DoctorResponse mapToResponse(Doctor d) {
        DoctorResponse r = new DoctorResponse();
        r.setId(d.getId());
        r.setFirstName(d.getFirstName());
        r.setLastName(d.getLastName());
        r.setFullName(d.getFullName());
        r.setEmail(d.getEmail());
        r.setPhone(d.getPhone());
        r.setSpecialization(d.getSpecialization());
        r.setQualification(d.getQualification());
        r.setExperienceYears(d.getExperienceYears());
        r.setLicenseNumber(d.getLicenseNumber());
        r.setConsultationFee(d.getConsultationFee());
        r.setAvailable(d.isAvailable());
        r.setDepartmentId(d.getDepartment() != null ? d.getDepartment().getId() : null);
        r.setDepartmentName(d.getDepartment() != null ? d.getDepartment().getName() : null);
        r.setCreatedAt(d.getCreatedAt());
        return r;
    }
}
