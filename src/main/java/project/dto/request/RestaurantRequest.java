package project.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest{
       @NotNull(message = "Restaurant name should not be null")
       private String name;
       @NotNull(message = "Restaurant location should not be null")
       private String location;
       @NotNull(message = "Restaurant rest type should not be null")
       private String restType;
       @NotNull(message = "Restaurant number of employees should not be null")
       private Integer numberOfEmployees;

       private Integer service;

}
