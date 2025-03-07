package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u WHERE u.active = true")
    Page<User> findAllByActiveTrue(Specification<User> spec, Pageable pageable);


    boolean existsByEmail(String email);

    //User findByRefreshTokenAndEmail(String token, String email);



}
