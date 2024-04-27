package com.santeut.auth.Auth.repository;

import com.santeut.auth.Auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByUserLoginId(String userLoginId);

    boolean existsByUserLoginId(String userLoginId);

    void deleteByUserLoginId(String userLoginId);

}