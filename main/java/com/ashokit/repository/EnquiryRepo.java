package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entities.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {

}
