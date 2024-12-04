package org.example.lab2springboot.controllers;

import org.example.lab2springboot.entities.Place;
import org.example.lab2springboot.repositories.PlaceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

        private final PlaceRepository placeRepository;

        public PlaceController(PlaceRepository placeRepository) {
                this.placeRepository = placeRepository;
        }

        @GetMapping("/public")
        public ResponseEntity<List<Place>> getAllPublicPlaces() {
                List<Place> publicPlaces = placeRepository.findByStatus(Place.Status.PUBLIC);
                return ResponseEntity.ok(publicPlaces);
        }

        @GetMapping("/public/{id}")
        public ResponseEntity<Place> getPublicPlaceById(@PathVariable Long id) {
                Optional<Place> place = placeRepository.findByIdAndStatus(id, Place.Status.PUBLIC);
                return place.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/public/category/{categoryId}")
        public ResponseEntity<List<Place>> getPublicPlacesByCategory(@PathVariable Long categoryId) {
                List<Place> places = placeRepository.findByCategoryIdAndStatus(categoryId, Place.Status.PUBLIC);
                return ResponseEntity.ok(places);
        }

        @GetMapping("/user")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<List<Place>> getUserPlaces(Principal principal) {
                Long userId = getUserIdFromPrincipal(principal);
                List<Place> places = placeRepository.findByUserId(userId);
                return ResponseEntity.ok(places);
        }

        @PostMapping
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<Place> createPlace(@RequestBody Place place, Principal principal) {
                place.setUserId(getUserIdFromPrincipal(principal));
                Place savedPlace = placeRepository.save(place);
                return ResponseEntity.ok(savedPlace);
        }

        @PutMapping("/{id}")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<Place> updatePlace(@PathVariable Long id, @RequestBody Place updatedPlace, Principal principal) {
                Optional<Place> existingPlace = placeRepository.findById(id);

                if (existingPlace.isPresent()) {
                        Place place = existingPlace.get();
                        if (!place.getUserId().equals(getUserIdFromPrincipal(principal))) {
                                return ResponseEntity.status(403).build(); // Förbjuden
                        }

                        place.setName(updatedPlace.getName());
                        place.setDescription(updatedPlace.getDescription());
                        place.setCoordinates(updatedPlace.getCoordinates());
                        place.setStatus(updatedPlace.getStatus());
                        Place savedPlace = placeRepository.save(place);
                        return ResponseEntity.ok(savedPlace);
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<Void> deletePlace(@PathVariable Long id, Principal principal) {
                Optional<Place> existingPlace = placeRepository.findById(id);

                if (existingPlace.isPresent()) {
                        Place place = existingPlace.get();
                        if (!place.getUserId().equals(getUserIdFromPrincipal(principal))) {
                                return ResponseEntity.status(403).build(); // Förbjuden
                        }

                        place.setStatus(Place.Status.PRIVATE);
                        placeRepository.save(place);
                        return ResponseEntity.noContent().build();
                } else {
                        return ResponseEntity.notFound().build();
                }
        }

        private Long getUserIdFromPrincipal(Principal principal) {
                return Long.parseLong(principal.getName());
        }
}
