package com.fitness.registration.repository;

import com.fitness.registration.model.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel,Integer> {
    @Query(value = "select * from user_roles ur \n" +
            "where ur.user_id = :accountId", nativeQuery = true)
    List<UserRoleModel> getRolesByAccountId(int accountId);
}
