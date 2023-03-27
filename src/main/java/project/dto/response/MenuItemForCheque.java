package project.dto.response;

import java.math.BigDecimal;

public record MenuItemForCheque(
         Long id,
         String name,
         BigDecimal price,
         Long amount
) {
}
