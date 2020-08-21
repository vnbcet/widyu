package com.widyu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.widyu.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
