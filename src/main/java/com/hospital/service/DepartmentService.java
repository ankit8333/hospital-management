package com.hospital.service;

import com.hospital.dto.DepartmentRequest;
import com.hospital.dto.DepartmentResponse;
import com.hospital.exception.DuplicateResourceException;
import com.hospital.exception.ResourceNotFoundException;
import com.hospital.model.Department;
import com.hospital.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentService {

    private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("Department '" + request.getName() + "' already exists");
        }
        Department dept = new Department();
        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        dept.setLocation(request.getLocation());
        dept.setHeadOfDepartment(request.getHeadOfDepartment());
        dept.setActive(true);

        Department saved = departmentRepository.save(dept);
        log.info("Created department: {}", saved.getName());
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public DepartmentResponse getDepartmentById(Long id) {
        return mapToResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findByActiveTrue().stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        Department dept = findById(id);
        if (!dept.getName().equalsIgnoreCase(request.getName()) &&
                departmentRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("Department name already exists");
        }
        dept.setName(request.getName());
        dept.setDescription(request.getDescription());
        dept.setLocation(request.getLocation());
        dept.setHeadOfDepartment(request.getHeadOfDepartment());
        return mapToResponse(departmentRepository.save(dept));
    }

    public void deleteDepartment(Long id) {
        Department dept = findById(id);
        dept.setActive(false);
        departmentRepository.save(dept);
    }

    private Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with ID: " + id));
    }

    private DepartmentResponse mapToResponse(Department d) {
        DepartmentResponse r = new DepartmentResponse();
        r.setId(d.getId());
        r.setName(d.getName());
        r.setDescription(d.getDescription());
        r.setLocation(d.getLocation());
        r.setHeadOfDepartment(d.getHeadOfDepartment());
        r.setActive(d.isActive());
        r.setDoctorCount(d.getDoctors() != null ? d.getDoctors().size() : 0);
        r.setCreatedAt(d.getCreatedAt());
        return r;
    }
}
