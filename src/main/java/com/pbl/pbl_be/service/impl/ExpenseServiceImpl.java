package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.model.Expense;
import com.pbl.pbl_be.repository.DonationRepository;
import com.pbl.pbl_be.repository.ExpenseRepository;
import com.pbl.pbl_be.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
@Service

public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;


    @Override
    public List<Expense> getExpensesByProjectId(Integer projectId) {
        return expenseRepository.findByProjectId(projectId);
    }

    @Override
    public Expense saveExpense(Expense expense) {
        expense.setCreatedAt(LocalDateTime.now());
        return expenseRepository.save(expense);
    }
}
