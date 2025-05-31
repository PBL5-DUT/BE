package com.pbl.pbl_be.service;

import com.pbl.pbl_be.model.Expense;

import java.util.List;

public interface ExpenseService {
    List<Expense> getExpensesByProjectId(Integer projectId);

    Expense saveExpense(Expense expense);
}
