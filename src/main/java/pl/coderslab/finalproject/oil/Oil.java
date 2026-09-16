package pl.coderslab.finalproject.oil;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Oil {

    @Id
    private LocalDate date;

    private Double price;
}
