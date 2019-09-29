package com.kocfinans.creditApplication.repository;

import com.kocfinans.creditApplication.entity.CreditApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditApplicationRepository extends JpaRepository<CreditApplication,Long> {

}
