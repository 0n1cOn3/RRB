package com.mykola.railroad.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Optional;

@Data
public class TrainSearchDTO {
    public Integer route;

    @Data
    public static class ByLength {
        public @NotNull Integer min;
        public @NotNull Integer max;
    }

    @Data
    public static class ByCost {
        public @NotNull Float min;
        public @NotNull Float max;
    }

    public Optional<ByLength> length = Optional.empty();
    public Optional<ByCost> cost = Optional.empty();

    public void setLength(ByLength length) {
        this.length = Optional.of(length);
    }

    public void setCost(ByCost cost) {
        this.cost = Optional.of(cost);
    }
}
