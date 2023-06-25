package com.blog.repositories;

import java.util.Optional;

import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByEmail(String email);// this customizes method help find the recorder by emialId
    Optional<User> findByUsernameOrEmail(String username, String email);//customize method.select * from user where email="email" or username="username"
    Optional<User>findByUsername(String username);//also write getByUserName
    Boolean existsByUsername(String username);// check whether userName Exist in dataBase.if exist cannot sign Up
    //mens if exist return true ,sign Up not done.if doesNt exist return false
    Boolean existsByEmail(String email);//check whether Email Exist in dataBase.if exist can't sign Up



}
