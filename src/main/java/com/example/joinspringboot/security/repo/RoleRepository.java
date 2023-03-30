package com.example.joinspringboot.security.repo;

import com.example.joinspringboot.security.model.ERole;
import com.example.joinspringboot.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(ERole name);

}