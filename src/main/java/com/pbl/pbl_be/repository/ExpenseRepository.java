package com.pbl.pbl_be.repository;

import com.pbl.pbl_be.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    @Query("SELECT e FROM Expense e WHERE e.project.projectId = ?1")
    List<Expense> findByProjectId(Integer projectId);
}