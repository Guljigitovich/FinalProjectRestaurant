package project.service.inter;

import project.dto.request.RestaurantRequest;
import project.dto.response.RestaurantResponse;
import project.dto.response.SimpleResponse;
import project.entity.Restaurant;

public interface RestaurantService {
    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);

    RestaurantResponse getByIdRestaurant(Long restaurantId);

    SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest);

    String deleteRestaurant(Long restaurantId);
}
