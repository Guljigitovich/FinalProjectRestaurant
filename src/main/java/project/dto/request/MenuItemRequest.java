package project.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemRequest {
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private String isVegetarian;
}
