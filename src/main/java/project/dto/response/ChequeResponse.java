package project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChequeResponse {
    private Long id;

    private BigDecimal priceAverage;
    private LocalDate createdAt;
    private String waiterFullName;

    private List<MenuItemForCheque> meals;
    private BigDecimal averagePrice;
    private int service;
    private BigDecimal grandTotal;

    public ChequeResponse(Long id, BigDecimal priceAverage, LocalDate createdAt, String waiterFullName, BigDecimal averagePrice, int service) {
        this.id = id;
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
        this.waiterFullName = waiterFullName;
        this.averagePrice = averagePrice;
        this.service = service;
    }
}

