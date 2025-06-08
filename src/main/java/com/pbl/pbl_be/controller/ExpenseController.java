package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.dto.DonationDTO;
import com.pbl.pbl_be.dto.ExpenseDTO;
import com.pbl.pbl_be.model.Donation;
import com.pbl.pbl_be.model.Expense;
import com.pbl.pbl_be.model.User;
import com.pbl.pbl_be.repository.UserRepository;
import com.pbl.pbl_be.repository.ExpenseRepository;
import com.pbl.pbl_be.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/project/{projectId}")
    public List<ExpenseDTO> getExpensesByProjectId(@PathVariable Integer projectId) {
        return expenseService.getExpensesByProjectId(projectId);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

    @PostMapping
    public ResponseEntity<Void> createExpense(@RequestBody ExpenseDTO dto) {
         this.expenseService.saveExpense(dto);
         return ResponseEntity.ok().build();
    }
}
