package com.mykola.railroad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;

public class EmployeeSearchDTO {
    // за стажем роботи на станції,
    @Data
    public static class ByExperience {
        public @NotNull Integer min;
        public @NotNull Integer max;
    }

    // статевою ознакою
    @Data
    public static class BySex {
        public @NotNull TypeSex sex;
    }

    // віком
    @Data
    public static class ByAge {
        public @NotNull Integer min;
        public @NotNull Integer max;
    }

    // ознакою наявності та кількості дітей
    @Data
    public static class ByChildren {
        public @NotNull Integer min;
        public @NotNull Integer max;
    }

    // розміру заробітної плати
    @Data
    public static class BySalary {
        public @NotNull Float min;
        public @NotNull Float max;
    }

    public Optional<ByExperience> experience;
    public Optional<BySex> sex;
    public Optional<ByAge> age;
    public Optional<ByChildren> children;
    public Optional<BySalary> salary;

    public void setExperience(ByExperience experience) {
        this.experience = Optional.of(experience);
    }

    public void setSex(BySex sex) {
        this.sex = Optional.of(sex);
    }

    public void setAge(ByAge age) {
        this.age = Optional.of(age);
    }

    public void setChildren(ByChildren children) {
        this.children = Optional.of(children);
    }
}
