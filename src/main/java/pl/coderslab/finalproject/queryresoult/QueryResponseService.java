package pl.coderslab.finalproject.queryresoult;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.charts.ChartData;
import pl.coderslab.finalproject.charts.ChartDataConverter;
import pl.coderslab.finalproject.charts.VisualizationChartAnalizer;
import pl.coderslab.finalproject.charts.VisualizationType;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryResponseService {
    private final ExecuteQueryService executeQueryService;
    private final ChartDataConverter chartDataConverter;
    private final VisualizationChartAnalizer visualizationChartAnalizer;

    public QueryResponse buildQueryResponse(String sql){
        QueryResult queryResult = executeQueryService.execute(sql);
        QueryResponse queryResponse = new QueryResponse();
        List<VisualizationType> visualizationTypes= visualizationChartAnalizer.analize(queryResult);

        ChartData chartData = null;
        if (visualizationTypes.stream()
                .anyMatch(t-> t!=VisualizationType.METRIC && t!= VisualizationType.TABLE)){
           chartData = chartDataConverter.convertToChartData(queryResult) ;
        }

        queryResponse.setQueryResult(queryResult);
        queryResponse.setChartData(chartData);
        queryResponse.setVisualizationTypes(visualizationTypes);
        return queryResponse;
    }
}
