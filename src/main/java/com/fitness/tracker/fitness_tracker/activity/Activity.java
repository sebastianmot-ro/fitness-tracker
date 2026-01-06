package com.fitness.tracker.fitness_tracker.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitness.tracker.fitness_tracker.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private Integer durationSeconds; //conversie la afisare

    private Integer distanceMeters;//conversie la afisare , la GYN,YOGA punem null

    private Integer calories;   //based on age/weight/hight

    private Double averagePace;    //run

    private Double averageSpeed;   //bike

    @Column(length = 1000)
    private String notes;

}
