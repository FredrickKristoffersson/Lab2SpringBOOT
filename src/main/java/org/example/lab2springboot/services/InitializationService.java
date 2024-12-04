package org.example.lab2springboot.services;

import org.example.lab2springboot.entities.Category;
import org.example.lab2springboot.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class InitializationService {

        private static final Logger logger = LoggerFactory.getLogger(InitializationService.class);

        @Autowired
        private CategoryRepository categoryRepository;

        @PostConstruct
        public void initializeCategories() {
                try {
                        logger.info("Initializing categories...");
                        if (categoryRepository.count() == 0) {
                                Category category1 = new Category("Kultur", "🎨", "En kategori för kulturella platser.");
                                Category category2 = new Category("Natur", "🌳", "En kategori för naturrelaterade platser.");

                                if (category1.getName() != null && !category1.getName().isEmpty() &&
                                        category2.getName() != null && !category2.getName().isEmpty()) {
                                        categoryRepository.saveAll(List.of(category1, category2));
                                        logger.info("Categories initialized successfully.");
                                } else {
                                        logger.error("Category names cannot be null or empty.");
                                }
                        }
                } catch (Exception e) {
                        logger.error("Error during category initialization: ", e);
                        throw e;
                }
        }
}

