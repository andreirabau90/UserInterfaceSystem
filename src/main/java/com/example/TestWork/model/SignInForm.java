package com.example.TestWork.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class SignInForm {
    @Size(min = 3, max = 16)
    private String userName;
    @Size(min = 3, max = 16)
    private String password;
}
