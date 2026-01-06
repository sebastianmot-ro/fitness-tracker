package com.fitness.tracker.fitness_tracker.user;

import com.fitness.tracker.fitness_tracker.activity.ActivityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO pentru ceea ce trimitem clientului
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;

    private List<ActivityResponse> activities;
}
