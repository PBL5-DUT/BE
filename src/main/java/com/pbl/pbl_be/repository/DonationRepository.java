package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
    Optional<Donation> findByTxnRef(String txnRef);
    List<Donation> findByProjectId(Integer projectId);
}

