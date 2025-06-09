package com.mykola.railroad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.util.Optional;

@Data
public class TrainSearchDTO {
    public Integer route;

    @Data
    public static class ByLength {
        public @Getter @NotNull Integer min;
        public @Getter @NotNull Integer max;
    }

    @Data
    public static class ByCost {
        public @Getter @NotNull Float min;
        public @Getter @NotNull Float max;
    }

    public @Getter Optional<ByLength> length = Optional.empty();
    public @Getter Optional<ByCost> cost = Optional.empty();

    public void setLength(ByLength length) {
        this.length = Optional.of(length);
    }

    public void setCost(ByCost cost) {
        this.cost = Optional.of(cost);
    }
}
