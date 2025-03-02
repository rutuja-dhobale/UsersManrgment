package com.app.repository;

import com.app.entity.Manager;
import com.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobNum(String mobNum);
    Optional<User> findByUserId(Long userId);
    @Query("SELECT u FROM User u WHERE u.manager.managerId = :managerId")
    List<User> findByManagerId(@Param("managerId") Long managerId);

}