package com.example.TestWork.repository;

import com.example.TestWork.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, Long> {
    UserAccount getByUserName(String userName);
    UserAccount getById(Long id);
}
