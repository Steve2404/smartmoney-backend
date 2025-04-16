package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.dto.TransactionRequestDTO;
import com.geekinstitut.smartmoney.dto.TransactionResponseDTO;
import com.geekinstitut.smartmoney.enums.CategoryType;
import com.geekinstitut.smartmoney.model.Category;
import com.geekinstitut.smartmoney.model.Expense;
import com.geekinstitut.smartmoney.repository.CategoryRepository;
import com.geekinstitut.smartmoney.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    private Category getCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    private void populateExpenseFromDTO(Expense expense, TransactionRequestDTO requestDTO) {
        Category category = getCategory(requestDTO.getCategoryId());
        if (category.getType() != CategoryType.EXPENSE) {
            throw new RuntimeException("Category type is not Expense");
        }
        expense.setCategory(category);
        expense.setAmount(requestDTO.getAmount());
        expense.setNote(requestDTO.getNote());
        expense.setDate(requestDTO.getDate());
    }

    public TransactionResponseDTO createExpense(TransactionRequestDTO requestDTO) {
        Expense expense = new Expense();
        populateExpenseFromDTO(expense, requestDTO);
        return expenseRepository.save(expense).toResponse();
    }

    public TransactionResponseDTO updateExpense(UUID id, TransactionRequestDTO requestDTO) {
        return expenseRepository.findById(id).map(expense -> {
            populateExpenseFromDTO(expense, requestDTO);
            return expenseRepository.save(expense).toResponse();
        }).orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }


    public void deleteExpenseById(UUID id) {
        if (!expenseRepository.existsById(id)) {
            throw new EntityNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
}
