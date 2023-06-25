package com.blog.security;

//CustomUserDetailsService are stander for all project.

import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

// It is responsible for loading a user's details (including roles) based on their username or email.
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {//If the user is not found, a UsernameNotFoundException is thrown.
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), mapRolesToAuthorities(user.getRoles()));//this method return username,password and role after match with DB
    }//here User is built in class which come form Security package(org.springframework.security.core.userdetails)

    //If the user is found, an instance of org.springframework.security.core.userdetails.User is created.
    // This class is provided by Spring Security and implements the UserDetails interface. It represents the authenticated user and provides necessary details such as username, password, and authorities.
    private Collection< ? extends GrantedAuthority>
    mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
//By implementing the UserDetailsService interface and customizing the user retrieval logic,
// the CustomUserDetailsService provides a way to load user details from the database and convert them into a format that can be used by Spring Security for authentication and authorization purposes.
