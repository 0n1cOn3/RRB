package com.mykola.railroad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InspectionSearchDTO {
    public @NotNull String from;
    public @NotNull String to;
}
