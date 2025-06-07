package com.pbl.pbl_be.service.impl;

import com.pbl.pbl_be.dto.DonationDTO;
import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.model.Expense;
import com.pbl.pbl_be.dto.ExpenseDTO;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.DonationRepository;
import com.pbl.pbl_be.repository.ExpenseRepository;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Expense> getExpensesByProjectId(Integer projectId) {
        return expenseRepository.findByProjectId(projectId);
    }

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAllWithUser()
                .stream()
                .map(ExpenseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Expense saveExpense(ExpenseDTO dto) {
        Expense expense = new Expense();
        expense.setProjectId(dto.getProjectId());
        expense.setSenderId(dto.getSenderId());
        expense.setAmount(dto.getAmount());
        expense.setPurpose(dto.getPurpose());
        expense.setCreatedAt(LocalDateTime.now());

        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found with id " + dto.getReceiverId()));
        expense.setReceiver(receiver);

        return expenseRepository.save(expense);
    }


}
