package com.fitness.tracker.fitness_tracker.activity;


import com.fitness.tracker.fitness_tracker.user.User;
import com.fitness.tracker.fitness_tracker.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;

    @PostMapping
    public ActivityResponse create(
            @PathVariable Long userId,
            @RequestBody @Valid ActivityRequest request) {

        User user = userService.getById(userId);
        if (user == null) throw new RuntimeException("User not found");

        Activity activity = new Activity();
        activity.setUser(user);
        activity.setType(request.getType());
        activity.setStartTime(request.getStartTime());
        activity.setDurationSeconds(request.getDurationSeconds());
        activity.setDistanceMeters(request.getDistanceMeters());
        activity.setCalories(request.getCalories());
        activity.setNotes(request.getNotes());

        calculateMetrics(activity);

        Activity saved = activityService.create(activity);
        return toResponse(saved);
    }

    @GetMapping
    public List<ActivityResponse> getAll(@PathVariable Long userId) {
        User user = userService.getById(userId);
        if (user == null) throw new RuntimeException("User not found");

        return user.getActivities().stream()
                .map(this::toResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        activityService.delete(id);
    }

    // Mapper Activity -> ActivityResponse
    private ActivityResponse toResponse(Activity activity){
        return ActivityResponse.builder()
                .id(activity.getId())
                .type(activity.getType().name())
                .startTime(activity.getStartTime())
                .durationSeconds(activity.getDurationSeconds())
                .distanceMeters(activity.getDistanceMeters())
                .calories(activity.getCalories())
                .averagePace(activity.getAveragePace())
                .averageSpeed(activity.getAverageSpeed())
                .notes(activity.getNotes())
                .build();
    }

    private void calculateMetrics(Activity activity) {
        if (activity.getDistanceMeters() != null && activity.getDurationSeconds() != null) {

            if (activity.getType() == ActivityType.RUN) {
                double pace = (double) (activity.getDurationSeconds() * 1000) / activity.getDistanceMeters();
                activity.setAveragePace(pace);
            }

            if (activity.getType() == ActivityType.BIKE) {
                double speed = (double) (activity.getDistanceMeters() * 100) / activity.getDurationSeconds();
                activity.setAverageSpeed(speed);
            }
        }
    }
}
