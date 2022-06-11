package com.rudraksh.springboot.service;

import com.rudraksh.springboot.model.Activity;
import com.rudraksh.springboot.model.CustomUser;
import com.rudraksh.springboot.repository.ActivityRepository;
import com.rudraksh.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService{

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(Activity theActivity) {
        // TODO Auto-generated method stub
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        CustomUser currentUser = userRepository.findByEmail(auth.getName());
        theActivity.setUser(currentUser);
        activityRepository.save(theActivity);
    }

    @Override
    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    @Override
    public List<Activity> findMyActivity() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.toString());
        System.out.println(auth.getPrincipal() instanceof CustomUser);

        CustomUser currentUser = userRepository.findByEmail(auth.getName());
        return activityRepository.findByUser(currentUser);
    }
    @Override
    public Optional<Activity> findById(int theId) {
        return activityRepository.findById(theId);
    }

    @Override
    public void deleteById(int theId) {
        // TODO Auto-generated method stub
        activityRepository.deleteById(theId);

    }
}
