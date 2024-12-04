package org.example.lab2springboot.repositories;

import org.example.lab2springboot.entities.Place;
import org.example.lab2springboot.entities.Place.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
        List<Place> findByStatus(Status status);

        Optional<Place> findByIdAndStatus(Long id, Place.Status status);

        List<Place> findByCategoryIdAndStatus(Long categoryId, Status status);

        List<Place> findByUserId(Long userId);
}
