package com.example.TestWork.entity;

import com.example.TestWork.enums.Role;
import com.example.TestWork.enums.Status;
import com.example.TestWork.model.UserForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_name")
    @Size(min = 3, max = 16, message = "username incorrect (3-16)")
    private String userName;
    @Column(name = "user_pass")
    @Size(min = 3, message = "size more 3  ")
    private String password;
    @Column(name = "user_firstName")
    @Size(min = 1, max = 16, message = "size name  incorrect (1-16)")
    private String firstName;
    @Column(name = "user_lastName")
    @Size(min = 1, max = 16, message = "size last name incorrect (1-16)")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private Status status;
    @Column(name = "createDate")
    private Date createDate;


    public UserAccount(UserForm userForm) {
        userName = userForm.getUserName();
        password = userForm.getPassword();
        firstName = userForm.getFirstName();
        lastName = userForm.getLastName();
        LocalDate localDate = LocalDate.now();
        createDate = Date.valueOf(localDate);
        role = userForm.getRole();
        status = Status.ACTIVE;
    }
}
