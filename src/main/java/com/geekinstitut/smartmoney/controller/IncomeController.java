package com.geekinstitut.smartmoney.controller;

import com.geekinstitut.smartmoney.dto.TransactionRequestDTO;
import com.geekinstitut.smartmoney.dto.TransactionResponseDTO;
import com.geekinstitut.smartmoney.service.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/incomes")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    /**
     * Récupérer toutes les transactions de type revenu.
     */
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllIncomes() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getIncomeById(@PathVariable UUID id) {
        return ResponseEntity.ok(incomeService.getIncomeById(id));
    }

    /**
     * Ajouter un revenu.
     */
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createIncome(@RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(incomeService.createIncome(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateIncome(@PathVariable UUID id, @RequestBody TransactionRequestDTO requestDTO) {
        return ResponseEntity.ok(incomeService.updateIncome(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable UUID id) {
        incomeService.deleteIncomeById(id);
        return ResponseEntity.noContent().build();
    }
}
