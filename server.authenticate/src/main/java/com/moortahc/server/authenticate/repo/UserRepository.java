package com.moortahc.server.authenticate.repo;

import com.moortahc.server.authenticate.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * @return an Optional that may contain a user with given email address or nothing
     */
    public Optional<UserEntity> findByEmailAddress(String emailAddress);
}
