package project.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.dto.request.RestaurantRequest;
import project.dto.response.RestaurantResponse;
import project.dto.response.SimpleResponse;
import project.entity.Restaurant;
import project.exception.NotFoundException;
import project.repositories.RestaurantRepository;
import project.service.inter.RestaurantService;

import java.util.NoSuchElementException;

@Service
@Transactional
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
      Restaurant restaurant = new Restaurant();
           restaurant.setName(restaurantRequest.getName());
           restaurant.setLocation(restaurantRequest.getLocation());
           restaurant.setService(restaurantRequest.getService());
           restaurant.setRestType(restaurantRequest.getRestType());
           restaurant.setNumberOfEmployees(restaurantRequest.getNumberOfEmployees());
         restaurantRepository.save(restaurant);
         log.info("Restaurant successfully saved");
         return SimpleResponse.builder().status(HttpStatus.CREATED).
                 message(String.format("This is : %s : restaurant successfully saved",restaurant.getName())).build();
    }

    @Override
    public RestaurantResponse getByIdRestaurant(Long restaurantId) {
        log.error("Restaurant doesn't exist");
        return restaurantRepository.getRestaurantById(restaurantId).
                orElseThrow(()->new NotFoundException("Restaurant with id " + restaurantId + "  doesn't exist"));
    }

    @Override
    public SimpleResponse updateRestaurant(Long restaurantId, RestaurantRequest restaurantRequest) {
        Restaurant newRestaurant = (restaurantRepository.findById(restaurantId).
                orElseThrow(() -> new NotFoundException("Restaurant with id " + restaurantId + "  doesn't exist")));
           newRestaurant.setName(restaurantRequest.getName());
           newRestaurant.setLocation(restaurantRequest.getLocation());
           newRestaurant.setRestType(restaurantRequest.getRestType());
           newRestaurant.setService(restaurantRequest.getService());
           newRestaurant.setNumberOfEmployees(restaurantRequest.getNumberOfEmployees());
           restaurantRepository.save(newRestaurant);
           log.info("Restaurant successfully updated");
        return SimpleResponse.builder().status(HttpStatus.CREATED).
                message(String.format("This is : %s : restaurant successfully updated",newRestaurant.getName())).build();
    }

    @Override
    public String deleteRestaurant(Long restaurantId) {
     boolean exists = restaurantRepository.existsById(restaurantId);
     log.error("Restaurant doesn't exist");
     if(!exists){
throw  new NotFoundException("Restaurant with id "+restaurantId+" doesn't exist");
     }
     restaurantRepository.deleteById(restaurantId);
     log.info("Restaurant deleted");
        return "Restaurant with id "+restaurantId+" is deleted";
    }
}
