package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.dto.TransactionRequestDTO;
import com.geekinstitut.smartmoney.dto.TransactionResponseDTO;
import com.geekinstitut.smartmoney.model.Category;
import com.geekinstitut.smartmoney.model.Expense;
import com.geekinstitut.smartmoney.model.Income;
import com.geekinstitut.smartmoney.repository.CategoryRepository;
import com.geekinstitut.smartmoney.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;


    public List<TransactionResponseDTO> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(Expense::toResponse)
                .collect(Collectors.toList());
    }


    public TransactionResponseDTO getExpenseById(UUID id) {
        return expenseRepository.findById(id).map(Expense::toResponse).orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }

    public TransactionResponseDTO createExpense(TransactionRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setAmount(requestDTO.getAmount());
        expense.setNote(requestDTO.getNote());
        expense.setDate(requestDTO.getDate());

        return expenseRepository.save(expense).toResponse();
    }

    public TransactionResponseDTO updateExpense(UUID id, TransactionRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return expenseRepository.findById(id)
                .map(expense -> {
                    expense.setCategory(category);
                    expense.setAmount(requestDTO.getAmount());
                    expense.setNote(requestDTO.getNote());
                    expense.setDate(requestDTO.getDate());
                    return expenseRepository.save(expense).toResponse();
                })
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));
    }

    public void deleteExpenseById(UUID id) {
        if (!expenseRepository.existsById(id)) {
            throw new EntityNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
}
