package pl.coderslab.finalproject.sale;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name="store_nbr")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_number")
    private Item item;

    private Double unitSales;
    private Boolean onPromotion;
}
