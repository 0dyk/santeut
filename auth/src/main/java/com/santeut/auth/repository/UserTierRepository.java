package com.santeut.auth.repository;

import com.santeut.auth.entity.UserTierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTierRepository extends JpaRepository<UserTierEntity, Integer> {


    Optional<UserTierEntity> findByUserId(int userId);
}