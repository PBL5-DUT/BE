package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.dto.DonationStatsDTO;
import com.pbl.pbl_be.model.Donation;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Integer> {


    @Query("SELECT d FROM Donation d JOIN FETCH d.user WHERE d.projectId = :projectId")
    List<Donation> findByProjectId(@Param("projectId") Integer projectId);


    Optional<Donation> findByTxnRef(String txnRef);

    @Query("SELECT d FROM Donation d JOIN FETCH d.user")
    List<Donation> findAllWithUser();


    @Query(value = "SELECT d.project_id AS projectId, DATE(d.created_at) AS createdAt, SUM(d.amount) AS amount " +
            "FROM donations d " +
            "GROUP BY d.project_id, DATE(d.created_at) " +
            "ORDER BY DATE(d.created_at) ASC", nativeQuery = true)
    List<DonationStatsDTO> sumDonationsByProjectAndDate();
}