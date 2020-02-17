package com.example.TestWork.validator;

import com.example.TestWork.model.UserForm;
import com.example.TestWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ValidatorImpl implements Validator {
    private final
    UserRepository userRepository;

    @Autowired
    public ValidatorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserForm.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.userForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmedPassword", "NotEmpty.userForm.confirmedPassword");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.userForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.userForm.lastName");

        if (!errors.hasFieldErrors("userName")) {
            if (userForm.getUserName().length() > 16) {
                errors.rejectValue("userName", "Length.userForm.userName");
            } else if (userRepository.getByUserName(userForm.getUserName()) != null) {
                errors.rejectValue("userName", "Duplicate.userForm.userName");

            }
        }
        if (!errors.hasFieldErrors("password")) {
            if (userForm.getPassword().length()<3){
                errors.rejectValue("password", "Length.userForm.password");
            }
           else if (!userForm.getPassword().equals(userForm.getConfirmedPassword())) {
                errors.rejectValue("password", "Math.userForm.password");
            }
        }
    }
}
