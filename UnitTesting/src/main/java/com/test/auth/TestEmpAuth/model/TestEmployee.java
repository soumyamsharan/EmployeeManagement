package com.test.auth.TestEmpAuth.model;

import lombok.Builder;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
@Builder
public class TestEmployee
{
    @PrimaryKey
    private Long employeeId;
    private String nameOfEmployee;
    private String nameOfDepartment;

    public TestEmployee(Long employeeId, String nameOfEmployee, String nameOfDepartment) {
        this.employeeId = employeeId;
        this.nameOfEmployee = nameOfEmployee;
        this.nameOfDepartment = nameOfDepartment;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getNameOfEmployee() {
        return nameOfEmployee;
    }

    public void setNameOfEmployee(String nameOfEmployee) {
        this.nameOfEmployee = nameOfEmployee;
    }

    public String getNameOfDepartment() {
        return nameOfDepartment;
    }

    public void setNameOfDepartment(String nameOfDepartment) {
        this.nameOfDepartment = nameOfDepartment;
    }
}
