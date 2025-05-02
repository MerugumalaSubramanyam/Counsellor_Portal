package com.ashokit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entities.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {

	Optional<Counsellor> findByEmailAndPwd(String email, String pwd);

	boolean existsByEmail(String email);
}
