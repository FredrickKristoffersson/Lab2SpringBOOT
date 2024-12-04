package org.example.lab2springboot.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Place {

        public enum Status {
                PUBLIC, PRIVATE
        }

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @ManyToOne
        @JoinColumn(name = "category_id", nullable = false)
        private Category category;

        private Long userId;

        @Enumerated(EnumType.STRING)
        private Status status = Status.PUBLIC;

        private String description;

        private String coordinates;

        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt = LocalDateTime.now();

        @Column(name = "updated_at")
        private LocalDateTime updatedAt = LocalDateTime.now();
}
