package com.mykola.railroad.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InspectionDTO {
    public Integer id;
    public Integer train;
    public TypeInspection inspectionType;
    public Date inspectedAt;
    public Boolean status;
    public String description;
}
