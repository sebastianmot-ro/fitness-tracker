package com.fitness.tracker.fitness_tracker.activity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityResponse {
        private Long id;
        private String type;
        private LocalDateTime startTime;
        private int durationSeconds;
        private double distanceMeters;
        private double calories;
        private Double averagePace;
        private Double averageSpeed;
        private String notes;
}
