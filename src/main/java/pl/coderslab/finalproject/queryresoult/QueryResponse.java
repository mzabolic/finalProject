package pl.coderslab.finalproject.queryresoult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.coderslab.finalproject.charts.ChartData;
import pl.coderslab.finalproject.charts.ChartDataConverter;
import pl.coderslab.finalproject.charts.VisualizationType;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class QueryResponse {
    QueryResult queryResult;
    ChartData chartData;
    List<VisualizationType> visualizationTypes;


}
