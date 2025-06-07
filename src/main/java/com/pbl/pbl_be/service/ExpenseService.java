package com.pbl.pbl_be.service;

import com.pbl.pbl_be.model.Expense;
import com.pbl.pbl_be.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseService {
    List<Expense> getExpensesByProjectId(Integer projectId);
    List<ExpenseDTO>getAllExpenses();
    Expense saveExpense(ExpenseDTO dto);
}