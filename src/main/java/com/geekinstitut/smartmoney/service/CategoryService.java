package com.geekinstitut.smartmoney.service;

import com.geekinstitut.smartmoney.model.Category;
import com.geekinstitut.smartmoney.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Récupère toutes les catégories.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Récupère une catégorie par son ID.
     */
    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    /**
     * Crée une nouvelle catégorie.
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Met à jour une catégorie existante.
     */
    public Category updateCategory(UUID id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(updatedCategory.getName());
                    existingCategory.setPlannedInMonth(updatedCategory.getPlannedInMonth());
                    existingCategory.setType(updatedCategory.getType());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    /**
     * Supprime une catégorie par son ID.
     */
    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
