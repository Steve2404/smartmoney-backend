package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.model.Income;
import com.geekinstitut.smartmoney.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public List<Income> getAllIncomes(){
        return incomeRepository.findAll();
    }

    public Optional<Income> getIncomeById(UUID id) {
        return incomeRepository.findById(id);
    }

    public Income createOrUpdate(Income income) {
        return incomeRepository.save(income);
    }

    public void deleteIncomeById(UUID id) {
        if (!incomeRepository.existsById(id)) {
            throw new EntityNotFoundException("Income not found with id: " + id);
        }
        incomeRepository.deleteById(id);
    }
}
