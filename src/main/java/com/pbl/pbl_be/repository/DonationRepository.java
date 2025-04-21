package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.dto.DonationDTO;
import com.pbl.pbl_be.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Integer> {
    Optional<Donation> findByTxnRef(String txnRef);
    List<Donation> findByProjectId(Integer projectId);
    @Query("SELECT d FROM Donation d JOIN FETCH d.user")
    List<Donation> findAllWithUser();

}

