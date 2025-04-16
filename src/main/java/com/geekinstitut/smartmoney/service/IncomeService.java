package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.dto.TransactionRequestDTO;
import com.geekinstitut.smartmoney.dto.TransactionResponseDTO;
import com.geekinstitut.smartmoney.enums.CategoryType;
import com.geekinstitut.smartmoney.model.Category;
import com.geekinstitut.smartmoney.model.Income;
import com.geekinstitut.smartmoney.repository.CategoryRepository;
import com.geekinstitut.smartmoney.repository.IncomeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final CategoryRepository categoryRepository;

    public List<TransactionResponseDTO> getAllIncomes() {
        return incomeRepository.findAll()
                .stream()
                .map(Income::toResponse)
                .collect(Collectors.toList());
    }

    public TransactionResponseDTO getIncomeById(UUID id) {
        return incomeRepository.findById(id).map(Income::toResponse).orElseThrow(() -> new RuntimeException("Income not found with id: " + id));
    }


    private Category getCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    private void populateIncomeFromDTO(Income income, TransactionRequestDTO requestDTO) {
        Category category = getCategory(requestDTO.getCategoryId());
        if (category.getType() != CategoryType.INCOME) {
            throw new RuntimeException("Category type is not Income");
        }
        income.setCategory(category);
        income.setAmount(requestDTO.getAmount());
        income.setNote(requestDTO.getNote());
        income.setDate(requestDTO.getDate());
    }

    public TransactionResponseDTO createIncome(TransactionRequestDTO requestDTO) {
        Income income = new Income();
        populateIncomeFromDTO(income, requestDTO);
        return incomeRepository.save(income).toResponse();
    }

    public TransactionResponseDTO updateIncome(UUID id, TransactionRequestDTO requestDTO) {
        return incomeRepository.findById(id).map(income -> {
            populateIncomeFromDTO(income, requestDTO);
            return incomeRepository.save(income).toResponse();
        }).orElseThrow(() -> new RuntimeException("Income not found with id: " + id));
    }

    public void deleteIncomeById(UUID id) {
        if (!incomeRepository.existsById(id)) {
            throw new EntityNotFoundException("Income not found with id: " + id);
        }
        incomeRepository.deleteById(id);
    }
}
