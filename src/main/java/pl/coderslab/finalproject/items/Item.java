package pl.coderslab.finalproject.items;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    private Long itemNumber;

    private String itemFamily;
    private Long itemClass;
    private boolean perishable;
}
