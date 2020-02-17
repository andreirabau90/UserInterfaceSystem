package com.example.TestWork.service;

import com.example.TestWork.entity.UserAccount;
import com.example.TestWork.model.SignInForm;
import com.example.TestWork.model.UserForm;
import com.example.TestWork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final
    UserRepository userRepository;
    private final
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccount getUserByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    public void saveUser(UserForm userForm) {
        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        userRepository.save(new UserAccount(userForm));
    }

    public UserAccount checkSignInForm(SignInForm signInForm) {
        UserAccount userAccount = userRepository.getByUserName(signInForm.getUserName());
        if (userAccount != null && passwordEncoder.matches(signInForm.getPassword(), userAccount.getPassword())) {
            return userAccount;
        }
        return null;
    }

    public UserAccount getById(Long id) {
        return userRepository.getById(id);
    }

    public List<UserAccount> getUserList() {
        return userRepository.findAll();
    }

    public void editUser(UserAccount userAccount) {
        UserAccount newUserAccount = userRepository.findById(userAccount.getId()).get();
        newUserAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        newUserAccount.setFirstName(userAccount.getFirstName());
        newUserAccount.setLastName(userAccount.getLastName());
        newUserAccount.setRole(userAccount.getRole());
        newUserAccount.setStatus(userAccount.getStatus());
        userRepository.saveAndFlush(newUserAccount);
    }
}
