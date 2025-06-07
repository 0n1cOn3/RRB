package com.mykola.railroad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Generic wrapper for returning list results with total count.
 */
@Data
@AllArgsConstructor
public class ListResult<T> {
    private List<T> items;
    private Integer total;
}
