package com.mykola.railroad.mapper;

import org.mapstruct.Mapper;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public class TimeDateMapper {
    public LocalTime toLocalTime(Time time) {
        return time.toLocalTime();
    }

    public Time fromLocalTime(LocalTime time) {
        return Time.valueOf(time);
    }

    public LocalDate toLocalDate(Date date) {
        return date.toLocalDate();
    }

    public Date fromLocalDate(LocalDate date) {
        return Date.valueOf(date);
    }
}
