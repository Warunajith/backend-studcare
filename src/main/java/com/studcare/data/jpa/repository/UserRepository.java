package com.studcare.data.jpa.repository;

import com.studcare.data.jpa.entity.User;
import com.studcare.data.jpa.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Optional<List<User>> findByRole(UserRole userRole);
}
