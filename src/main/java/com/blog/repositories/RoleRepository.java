package com.blog.repositories;

import java.util.Optional;

import com.blog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);//find by name(admin,user) based on role

}
