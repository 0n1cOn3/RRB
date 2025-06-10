package com.mykola.railroad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

public class CustomerSearchDTO {
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

    public @Getter Optional<BySex> sex = Optional.empty();
    public @Getter Optional<ByAge> age = Optional.empty();

    public void setSex(BySex sex) {
        this.sex = Optional.of(sex);
    }

    public void setAge(ByAge age) {
        this.age = Optional.of(age);
    }
}
