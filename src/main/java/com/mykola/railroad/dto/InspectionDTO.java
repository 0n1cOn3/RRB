package com.mykola.railroad.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class InspectionDTO {
    public Integer id;
    public Integer train;
    public String inspectionType;
    public Date inspectedAt;
    public Boolean status;
    public String description;
}
