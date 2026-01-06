package com.fitness.tracker.fitness_tracker.activity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public Activity create(Activity activity) {
        return activityRepository.save(activity);
    }

    public List<Activity> allByUser(Long userId) {
        return activityRepository.findByUserId(userId);
    }

    public Activity getById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        activityRepository.deleteById(id);
    }
}
