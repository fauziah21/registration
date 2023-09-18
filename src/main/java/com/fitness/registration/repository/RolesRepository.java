package com.fitness.registration.repository;

import com.fitness.registration.model.ERole;
import com.fitness.registration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
