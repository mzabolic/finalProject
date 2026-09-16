package pl.coderslab.finalproject.stores;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Store {
    @Id
    @NotNull(message = "Store number is required")
    @Positive(message = "Store number must be positive")
    private Integer storeNumber;

    @NotBlank(message = "City is required")
    @Size(max = 100)
    private String city;

    @NotBlank(message = "State is required")
    @NotBlank(message = "State is required")
    private String state;

    @NotNull
    private char type;

    @NotNull(message = "Cluster is required")
    @Min(value = 1, message = "Cluster must be at least 1")
    @Max(value = 100, message = "Cluster must not exceed 100")
    private int cluster;
}

