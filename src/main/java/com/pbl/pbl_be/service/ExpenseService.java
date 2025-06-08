package com.pbl.pbl_be.service;

import com.pbl.pbl_be.model.Expense;
import com.pbl.pbl_be.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO> getExpensesByProjectId(Integer projectId);
    List<ExpenseDTO>getAllExpenses();
    void saveExpense(ExpenseDTO dto);
}