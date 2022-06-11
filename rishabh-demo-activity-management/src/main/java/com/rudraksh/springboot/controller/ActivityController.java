package com.rudraksh.springboot.controller;


import com.rudraksh.springboot.mapper.MapStructMapper;
import com.rudraksh.springboot.model.Activity;
import com.rudraksh.springboot.repository.ActivityRepository;
import com.rudraksh.springboot.service.ActivityService;
import com.rudraksh.springboot.service.UserService;
import com.rudraksh.springboot.web.dto.ActivityWithUser;
import com.rudraksh.springboot.web.dto.IActivityWithUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private MapStructMapper mapStructMapper;

    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;

    @Autowired
    private ActivityRepository activityRepository;

    @PostMapping("/saveActivity")
    public String saveActivity(@Valid @ModelAttribute("activity") Activity theActivity, BindingResult theBindingResult) {
        if (theBindingResult.hasErrors())
            return "add-activity";
        activityService.save(theActivity);
        return "redirect:/api/activities/showMyActivity";
    }


    @GetMapping("/showActivity")
    public String showActivity(Model theModel) {
        List<IActivityWithUser> iActivity = activityRepository.listActivity();
        List<ActivityWithUser> listOfActivities = mapStructMapper.iActivityWithUsertoActivityWithUser(iActivity);
//        List<ActivityWithUser> listOfActivities = new ArrayList<>();
//        for (IActivityWithUser i : iActivity){
//            listOfActivities.add(new ActivityWithUser(i.getFirstName(),i.getLastName(),i.getActivityName(),i.getActivityDesc(),i.getDeadlineDate()));
//        }
//        listOfActivities.forEach(i -> System.out.println(i.toString()));
        theModel.addAttribute("activity", listOfActivities);
        return "show-all-activities";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("activityId") int theId, Model theModel) {
        Activity theActivity = activityService.findById(theId).orElseThrow(() -> new RuntimeException("Activity not found"));
        theModel.addAttribute("activity", theActivity);
        return "add-activity";
    }

    @GetMapping("/showActivityForm")
    public String showActivityForm(Model model) {
        model.addAttribute("activity", new Activity());
        return "add-activity";
    }

    @GetMapping("/showMyActivity")
    public String showMyActivity(Model theModel) {
        List<Activity> theActivity = activityService.findMyActivity();
        theModel.addAttribute("activity", theActivity);
        return "my-activities";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("activityId") int theId) {
        activityService.deleteById(theId);
        return "redirect:/api/activities/showMyActivity";
    }
}
