package com.pbl.pbl_be.controller;

import com.pbl.pbl_be.model.Expense;
import com.pbl.pbl_be.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")

public class ExpenseController {

        @Autowired
        private ExpenseService expenseService;
        @GetMapping("/project/{projectId}")
        public List<Expense> getExpensesByProjectId(@PathVariable Integer projectId) {
            return expenseService.getExpensesByProjectId(projectId);
        }
    }

