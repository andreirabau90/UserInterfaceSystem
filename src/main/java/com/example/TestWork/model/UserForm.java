package com.example.TestWork.model;

import com.example.TestWork.enums.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserForm {
    private String userName;
    private String password;
    private String confirmedPassword;
    private String firstName;
    private String lastName;
    private Role role;
}
