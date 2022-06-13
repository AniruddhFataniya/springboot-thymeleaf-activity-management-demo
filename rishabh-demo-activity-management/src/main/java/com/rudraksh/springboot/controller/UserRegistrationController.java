package com.rudraksh.springboot.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.rudraksh.springboot.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rudraksh.springboot.model.Activity;
import com.rudraksh.springboot.model.CustomUser;
import com.rudraksh.springboot.repository.ActivityRepository;
import com.rudraksh.springboot.repository.UserRepository;
import com.rudraksh.springboot.service.UserService;

import com.rudraksh.springboot.web.dto.UserRegistrationDto;


@Controller
public class UserRegistrationController {

    private UserService userService;

    private ActivityService activityService;

    @Autowired
    public UserRegistrationController(UserService userService, ActivityService activityService) {
        super();
        this.userService = userService;
        this.activityService = activityService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping("/showTeacherSignupForm")
    public String showTeacherRegistrationForm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "sign-up-teacher";
        return "redirect:/home";
    }

    @GetMapping("/showStudentSignupForm")
    public String showStudentRegistrationForm() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "sign-up-student";
        return "redirect:/home";
    }

//    @PostMapping("/saveTeacher")
//    public String registerTeacherAccount(@Valid @ModelAttribute("user") UserRegistrationDto user, BindingResult theBindingResult,
//                                         Model model, HttpSession session) {
//        if (theBindingResult.hasErrors())
//            return "sign-up-teacher";
//        try {
//            userService.findByEmail(user.getEmail()).orElseThrow(() -> new Exception("User already Exists"));
//            userService.saveTeacher(user);
//            return "redirect:/";
//        } catch (Exception e) {
//            //session.setAttribute("message",);
//            model.addAttribute("user", new CustomUser());
//            return "sign-up-teacher";
//        }
//    }


	@PostMapping("/saveTeacher")
	public String registerTeacherAccount(@Valid @ModelAttribute("user") UserRegistrationDto user, BindingResult theBindingResult,
										 Model theModel) {
		if (theBindingResult.hasErrors()) {
			return "sign-up-teacher";
		}
		Optional<CustomUser> existing = userService.findByEmail(user.getEmail());
		if (existing.isPresent()) {
			theModel.addAttribute("user", user);
			theModel.addAttribute("registrationError", "Email already exists.");
			return "sign-up-teacher";
		}
		userService.saveTeacher(user);
		return "redirect:/";
	}

    @PostMapping("/updateTeacher")
    public String updateTeacherAccount(@Valid @ModelAttribute("user") UserRegistrationDto registrationDto, BindingResult theBindingResult,
                                       Model theModel) {
        // form validation
        if (theBindingResult.hasErrors()) {
            return "update-profile";
        }
        userService.saveTeacher(registrationDto);
        return "redirect:/accountSetting";
    }


//    @PostMapping("/saveStudent")
//    public String registerStudentAccount(@Valid @ModelAttribute("user") UserRegistrationDto user, BindingResult theBindingResult,
//                                         Model model) {
//        String userName = registrationDto.getEmail();
//        if (theBindingResult.hasErrors()) {
//            return "sign-up-student";
//        }
//		userService.findByEmail(user)
//        OpCustomUser existing = userService.findByEmail(userName);
//        if (existing != null) {
//            theModel.addAttribute("user", new UserRegistrationDto());
//            theModel.addAttribute("registrationError", "Email already exists.");
//            return "sign-up-student";
//        }
//        userService.saveStudent(registrationDto);
//        return "redirect:/login";
//    }

    @GetMapping("/accountSetting")
    public String accountSetting(Model theModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return "sign-in";
        List<CustomUser> theUser = userService.listMe();
        theModel.addAttribute("user", theUser);
        return "account-setting";
    }

    @GetMapping("/showFormForUpdateProfile")
    public String showFormForUpdateProfile(@RequestParam("userId") Long theId, Model theModel) {
        CustomUser theUser = userService.findUserById(theId);
        theModel.addAttribute("user", theUser);
        return "update-profile";
    }


}
