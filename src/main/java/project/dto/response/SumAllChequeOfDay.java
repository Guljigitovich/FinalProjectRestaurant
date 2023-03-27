package project.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class SumAllChequeOfDay {
     private String waiter;
     private LocalDate date;
     private Long counterOfCheque;
     private BigDecimal totalSumma;

    public SumAllChequeOfDay(String waiter, LocalDate date) {
        this.waiter = waiter;
        this.date = date;
    }
}
