package project.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.request.RestaurantRequest;
import project.dto.response.RestaurantResponse;
import project.dto.response.SimpleResponse;
import project.entity.Restaurant;
import project.service.inter.RestaurantService;

import java.util.Optional;

@RequestMapping("/api/restaurants")
@RestController
public class RestaurantApi {
   private final RestaurantService restaurantService;

    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
@PostMapping
@PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveRestaurant(@RequestBody RestaurantRequest restaurantRequest){
      return restaurantService.saveRestaurant(restaurantRequest);
}

@GetMapping("/{restaurantId}")
@PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public RestaurantResponse getById (@PathVariable Long restaurantId){
        return restaurantService.getByIdRestaurant(restaurantId);

}
@PutMapping("/{restaurantId}")
@PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateRestaurant (@PathVariable Long restaurantId,
                                        @RequestBody RestaurantRequest restaurantRequest){
        return restaurantService.updateRestaurant(restaurantId,restaurantRequest);
}

@DeleteMapping("/{restaurantId}")
@PreAuthorize("hasAuthority('ADMIN')")
    public String deleteRestaurant(@PathVariable Long restaurantId){
        return restaurantService.deleteRestaurant(restaurantId);
}




}
