package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.ExpenseDTO;
import com.pbl.pbl_be.mapper.ExpenseMapper;
import com.pbl.pbl_be.model.Expense;
import com.pbl.pbl_be.repository.ExpenseRepository;
import com.pbl.pbl_be.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    @Override
    public List<ExpenseDTO> getExpensesByProjectId(Integer projectId) {
        List<Expense> expenses = expenseRepository.findByProjectId(projectId);
        if (expenses != null && !expenses.isEmpty()) {
            return expenses.stream()
                    .map(expense -> expenseMapper.toDto(expense))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAllWithUser();
        if (expenses != null && !expenses.isEmpty()) {
            return expenses.stream()
                    .map(expense -> expenseMapper.toDto(expense))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void saveExpense(ExpenseDTO dto) {
        Expense expense = expenseMapper.toEntity(dto);
        this.expenseRepository.save(expense);
    }
}