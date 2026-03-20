package com.hospital.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
public class Department extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(length = 500)
    private String description;
    private String location;
    private String headOfDepartment;
    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Doctor> doctors;

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
    public List<Doctor> getDoctors() { return doctors; }
    public void setDoctors(List<Doctor> doctors) { this.doctors = doctors; }
}
