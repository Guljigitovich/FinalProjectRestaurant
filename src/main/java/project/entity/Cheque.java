package project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cheques")
@NoArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq")
    private Long id;
    private BigDecimal priceAverage;
    private LocalDate createdAt;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private User user;

    @ManyToMany(mappedBy = "cheques", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<MenuItem> menuItems = new ArrayList<>();

}