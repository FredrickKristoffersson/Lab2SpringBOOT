package org.example.lab2springboot.controllers;

import org.example.lab2springboot.entities.Category;
import org.example.lab2springboot.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

        private final CategoryRepository categoryRepository;

        public CategoryController(CategoryRepository categoryRepository) {
                this.categoryRepository = categoryRepository;
        }

        // Hämta alla kategorier
        @GetMapping
        public ResponseEntity<List<Category>> getAllCategories() {
                List<Category> categories = categoryRepository.findAll();
                return ResponseEntity.ok(categories);
        }

        // Hämta en kategori via namn
        @GetMapping("/{name}")
        public ResponseEntity<Object> getCategoryByName(@PathVariable String name) {
                return categoryRepository.findByName(name)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }

        // Skapa en ny kategori
        @PostMapping
        public ResponseEntity<Category> createCategory(@RequestBody Category category) {
                if (category.getName() == null || category.getName().isEmpty()) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
                Category savedCategory = categoryRepository.save(category);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        }
}
