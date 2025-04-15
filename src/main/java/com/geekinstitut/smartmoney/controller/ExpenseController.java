package com.geekinstitut.smartmoney.controller;

import com.geekinstitut.smartmoney.dto.TransactionRequestDTO;
import com.geekinstitut.smartmoney.dto.TransactionResponseDTO;
import com.geekinstitut.smartmoney.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    /**
     * Récupérer toutes les transactions de type dépense.
     */
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.getAllExpenses());
    }

     @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getExpenseById(@PathVariable UUID id) {
        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    /**
     * Ajouter une dépense.
     */
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createExpense(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(expenseService.createExpense(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateExpense(@PathVariable UUID id, @RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(expenseService.updateExpense(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> deleteExpense(@PathVariable UUID id) {
        expenseService.deleteExpenseById(id);
        return ResponseEntity.noContent().build();
    }
}
