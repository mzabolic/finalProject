package pl.coderslab.finalproject.stores;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Store {
    @Id
    private Long storeNbr;
    private String city;
    private String state;
    private char type;
    private int cluster;
}
