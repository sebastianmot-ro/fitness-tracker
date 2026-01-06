package com.fitness.tracker.fitness_tracker.activity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ActivityRequest {

    @NotNull
    private ActivityType type;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    @Min(1)
    private Integer durationSeconds;

    @Min(1)
    private Integer distanceMeters;

    @Min(1)
    private Integer calories;

    private String notes;
}
