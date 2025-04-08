package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.model.Expense;
import com.geekinstitut.smartmoney.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> getAllIncomes(){
        return expenseRepository.findAll();
    }

    public Optional<Expense> getIncomeById(UUID id) {
        return expenseRepository.findById(id);
    }

    public Expense createOrUpdate(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpenseById(UUID id) {
        if (!expenseRepository.existsById(id)) {
            throw new EntityNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
}
