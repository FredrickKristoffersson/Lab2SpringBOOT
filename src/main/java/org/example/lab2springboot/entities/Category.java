package org.example.lab2springboot.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Category {
        public Category() {}
        public Category(String a, String b, String c) {}

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true)
        @NotNull(message = "Kan inte vara tomt")
        private String name;

        private String symbol;

        private String description;

        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(name = "updated_at")
        private LocalDateTime updatedAt = LocalDateTime.now();
}
