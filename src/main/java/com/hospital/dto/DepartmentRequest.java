package com.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class DepartmentRequest {
    @NotBlank(message = "Department name is required")
    private String name;
    private String description;
    private String location;
    private String headOfDepartment;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getHeadOfDepartment() { return headOfDepartment; }
    public void setHeadOfDepartment(String h) { this.headOfDepartment = h; }
}
