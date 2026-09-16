package pl.coderslab.finalproject.sale;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.finalproject.items.Item;
import pl.coderslab.finalproject.stores.Store;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    private Long id;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name="store_number")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_number")
    private Item item;

    private Double unitSales;
    private Boolean onPromotion;
}
