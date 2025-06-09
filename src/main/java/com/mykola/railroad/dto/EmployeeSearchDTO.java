package com.mykola.railroad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

public class EmployeeSearchDTO {
    // за стажем роботи на станції,
    @Data
    public static class ByExperience {
        public @NotNull @Getter Integer min;
        public @NotNull @Getter Integer max;
    }

    // статевою ознакою
    @Data
    public static class BySex {
        public @NotNull @Getter TypeSex sex;
    }

    // віком
    @Data
    public static class ByAge {
        public @NotNull @Getter Integer min;
        public @NotNull @Getter Integer max;
    }

    // ознакою наявності та кількості дітей
    @Data
    public static class ByChildren {
        public @NotNull @Getter Integer min;
        public @NotNull @Getter Integer max;
    }

    // розміру заробітної плати
    @Data
    public static class BySalary {
        public @NotNull @Getter Float min;
        public @NotNull @Getter Float max;
    }

    public @Getter Optional<ByExperience> experience = Optional.empty();
    public @Getter Optional<BySex> sex = Optional.empty();
    public @Getter Optional<ByAge> age = Optional.empty();
    public @Getter Optional<ByChildren> children = Optional.empty();
    public @Getter Optional<BySalary> salary = Optional.empty();

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

    public void setSalary(BySalary salary) {
        this.salary = Optional.of(salary);
    }
}
