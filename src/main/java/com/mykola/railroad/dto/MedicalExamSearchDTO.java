package com.mykola.railroad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalExamSearchDTO {
    public @NotNull EmployeeSearchDTO employee;
    public @NotNull Integer year;
    public @NotNull Boolean verdict;
}
