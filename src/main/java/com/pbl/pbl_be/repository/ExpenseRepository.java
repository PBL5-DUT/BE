package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query("SELECT e FROM Expense e WHERE e.projectId = ?1")
    List<Expense> findByProjectId(Integer projectId);

    @Query("SELECT e FROM Expense e JOIN FETCH e.receiver")
    List<Expense> findAllWithUser();
}