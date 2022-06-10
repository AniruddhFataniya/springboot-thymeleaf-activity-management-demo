package com.rudraksh.springboot.service;

import com.rudraksh.springboot.model.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {

    void save(Activity theActivity);

    List<Activity> findAll();

    Optional<Activity> findById(int theId);

    void deleteById(int theId);
}
