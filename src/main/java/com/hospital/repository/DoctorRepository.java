package com.hospital.repository;

import com.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findByAvailableTrue();
    List<Doctor> findBySpecializationIgnoreCase(String specialization);
    List<Doctor> findByDepartmentId(Long departmentId);
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%',:name,'%')) OR LOWER(d.lastName) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<Doctor> searchByName(String name);
    boolean existsByEmail(String email);
    boolean existsByLicenseNumber(String licenseNumber);
}
