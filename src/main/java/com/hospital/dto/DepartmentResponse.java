package com.hospital.dto;

import java.time.LocalDateTime;

public class DepartmentResponse {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String headOfDepartment;
    private boolean active;
    private int doctorCount;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getHeadOfDepartment() { return headOfDepartment; }
    public void setHeadOfDepartment(String h) { this.headOfDepartment = h; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getDoctorCount() { return doctorCount; }
    public void setDoctorCount(int doctorCount) { this.doctorCount = doctorCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
