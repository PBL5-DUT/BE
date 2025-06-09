package com.pbl.pbl_be.mapper;

import com.pbl.pbl_be.dto.ExpenseDTO;
import com.pbl.pbl_be.model.Expense;
import com.pbl.pbl_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpenseMapper {

    @Autowired
    private UserRepository userRepository;

     public ExpenseDTO toDto(Expense expense) {
         if (expense == null) {
             return null;
         }
         ExpenseDTO dto = new ExpenseDTO();
         dto.setExpenseId(expense.getExpenseId());
            dto.setProjectId(expense.getProjectId());
            dto.setSenderId(expense.getSenderId());
            dto.setReceiverId(expense.getReceiver() != null ? expense.getReceiver().getUserId() : null);
            dto.setAmount(expense.getAmount());
            dto.setPurpose(expense.getPurpose());
            dto.setCreatedAt(expense.getCreatedAt());

         return dto;
     }

     public Expense toEntity(ExpenseDTO dto) {
         if (dto == null) {
             return null;
         }
         Expense expense = new Expense();
         expense.setExpenseId(dto.getExpenseId());
            expense.setProjectId(dto.getProjectId());
            expense.setSenderId(dto.getSenderId());
            expense.setReceiver(userRepository.findById(dto.getReceiverId()).orElse(null));
            expense.setAmount(dto.getAmount());
            expense.setPurpose(dto.getPurpose());
            expense.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());

         return expense;
     }
}
