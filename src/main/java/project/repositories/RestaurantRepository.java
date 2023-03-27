package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.dto.response.RestaurantResponse;
import project.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<RestaurantResponse> getRestaurantById(Long restaurantId);
}