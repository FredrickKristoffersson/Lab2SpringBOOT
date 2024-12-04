package org.example.lab2springboot.repositories;

import org.example.lab2springboot.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        boolean existsByName(String name);

        Optional<Object> findByName(String name);
}
