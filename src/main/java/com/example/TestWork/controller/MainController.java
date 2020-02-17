package com.example.TestWork.controller;

import com.example.TestWork.entity.UserAccount;
import com.example.TestWork.enums.Status;
import com.example.TestWork.model.SignInForm;
import com.example.TestWork.model.UserForm;
import com.example.TestWork.service.UserService;
import com.example.TestWork.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class MainController {
    private final ValidatorImpl validator;
    private final UserService userService;

    @Autowired
    public MainController(ValidatorImpl validator, UserService userService) {
        this.validator = validator;
        this.userService = userService;
    }

    @InitBinder
    private void InitBinder(WebDataBinder webDataBinder) {
        Object target = webDataBinder.getTarget();
        if (target instanceof UserForm) {
            webDataBinder.setValidator(validator);
        }
    }

    @GetMapping("/")
    private String greeting() {
        return "/index";
    }

    @GetMapping("/registration")
    private ModelAndView registration(@ModelAttribute(binding = false) UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView("registrationPage");
        if (userAccount.getId() != 0) {
            modelAndView.addObject("userAccount", userAccount);
        }
        modelAndView.addObject("userForm", new UserForm());
        return modelAndView;
    }

    @PostMapping("/createUser")
    private ModelAndView createUser(@ModelAttribute(binding = false) UserAccount userAccount,
                                    @ModelAttribute @Validated UserForm userForm,
                                    BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("registrationPage");
        if (!bindingResult.hasErrors()) {
            userService.saveUser(userForm);
            modelAndView.setViewName("index");
            if (userAccount.getId() != 0) {
                return returnUser(userAccount);
            }
        }
        return modelAndView;

    }

    @GetMapping("/login")
    private ModelAndView getSignIn() {
        ModelAndView modelAndView = new ModelAndView("loginPage");
        modelAndView.addObject("signInForm", new SignInForm());
        return modelAndView;
    }

    @PostMapping("/login")
    private ModelAndView getSignIn(@Valid SignInForm signInForm,
                                   BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView("loginPage");
        if (!bindingResult.hasErrors()) {
            UserAccount userAccount = userService.checkSignInForm(signInForm);
            if (userAccount == null || userAccount.getStatus() == Status.INACTIVE) {
                bindingResult.addError(new FieldError("signInForm", "userName", "Incorrect data entered or your profile is blocked"));
            } else {
                modelAndView.setViewName("mainPage");
                modelAndView.addObject("userAccount", userAccount);
                return modelAndView;
            }
        }
        modelAndView.addObject("signInPage",signInForm);
        return modelAndView;
    }

    @GetMapping("/user")
    private ModelAndView getLisrUser(@ModelAttribute UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("userAccount", userAccount);
        modelAndView.addObject("list", userService.getUserList());
        return modelAndView;
    }

    @GetMapping("/user/id")
    private ModelAndView getUserById(@ModelAttribute UserAccount userAccount,
                                     @RequestParam long idUser) {
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("userAccount", userAccount);
        modelAndView.addObject("editUser", userService.getById(idUser));
        return modelAndView;
    }

    @GetMapping("/return")
    private ModelAndView returnUser(@ModelAttribute UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView("mainPage");
        modelAndView.addObject("userAccount", userAccount);
        return modelAndView;
    }

    @GetMapping("edit")
    private ModelAndView editPage(@ModelAttribute("editUser") UserAccount editUser,
                                  @ModelAttribute("userAccount") UserAccount userAccount) {
        ModelAndView modelAndView = new ModelAndView("editPage");
        modelAndView.addObject("userAccount", userAccount);
        modelAndView.addObject("editUser", editUser);
        return modelAndView;
    }

    @PostMapping("user/id/edit")
    private ModelAndView editUser(@RequestParam("idUser") long id,
                                  @ModelAttribute("editUser") @Validated UserAccount editUser,
                                  BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("mainPage");
        modelAndView.addObject("userAccount", userService.getById(id));
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("editUser", editUser);
            modelAndView.setViewName("editPage");
            return modelAndView;
        }
        userService.editUser(editUser);
        return modelAndView;
    }

}