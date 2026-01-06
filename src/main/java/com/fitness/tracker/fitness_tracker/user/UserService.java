package com.fitness.tracker.fitness_tracker.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(User user){
        return userRepository.save(user);
    }

    public List<User> all(){
        return userRepository.findAll();
    }

    public User getById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
