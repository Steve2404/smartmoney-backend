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


    public TransactionResponseDTO createIncome(TransactionRequestDTO requestDTO) {
        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if(category.getType() != CategoryType.INCOME) {
            throw new RuntimeException("Category type is not INCOME");
        }
        Income income = new Income();
        income.setCategory(category);
        income.setAmount(requestDTO.getAmount());
        income.setNote(requestDTO.getNote());
        income.setDate(requestDTO.getDate());

        return incomeRepository.save(income).toResponse();
    }

    public TransactionResponseDTO updateIncome(UUID id, TransactionRequestDTO requestDTO) {
        var category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if(category.getType() != CategoryType.INCOME) {
            throw new RuntimeException("Category type is not INCOME");
        }

        return incomeRepository.findById(id)
                .map(income ->{
                    income.setCategory(category);
                    income.setAmount(requestDTO.getAmount());
                    income.setNote(requestDTO.getNote());
                    income.setDate(requestDTO.getDate());
                    return incomeRepository.save(income).toResponse();

                })
                .orElseThrow(() -> new RuntimeException("Income not found with id: " + id));
    }

    public void deleteIncomeById(UUID id) {
        if (!incomeRepository.existsById(id)) {
            throw new EntityNotFoundException("Income not found with id: " + id);
        }
        incomeRepository.deleteById(id);
    }
}
