package pl.coderslab.finalproject.analytics;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DashboeardStatisticsDto {
    private Long storeCount;
    private Long productsCount;
    private Long saleRecordsCount;
    private Long combinedSales;
}
