package project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantResponse{
        private Long id;
        private String name;
        private String location;
        private String restType;
        private Integer numberOfEmployees;
        private Integer service;

}
