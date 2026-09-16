package pl.coderslab.finalproject.charts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.dataprofila.ColumnKind;
import pl.coderslab.finalproject.dataprofila.DataProfile;
import pl.coderslab.finalproject.dataprofila.DataProfiler;
import pl.coderslab.finalproject.queryresoult.QueryResult;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisualizationChartAnalizer {
    private final DataProfiler dataProfiler;

    public List<VisualizationType> analize(QueryResult queryResult){
        List<VisualizationType> visualizationTypeList = new ArrayList<>();

        DataProfile dataProfile = dataProfiler.profile(queryResult);

        if (dataProfile.getColumnCount() == 1 && dataProfile.getRowCount() == 1){
            visualizationTypeList.add(VisualizationType.METRIC);
        }

        if (dataProfile.getColumnCount() == 2 && dataProfile.getFirstColumnKind() == ColumnKind.TEXT && dataProfile.getSecondColumnKind() == ColumnKind.NUMBER){
            visualizationTypeList.add(VisualizationType.BAR_CHART);
            visualizationTypeList.add(VisualizationType.PIE_CHART);
        }

        if (dataProfile.getColumnCount() == 2 && dataProfile.getFirstColumnKind() == ColumnKind.DATE && dataProfile.getSecondColumnKind() == ColumnKind.NUMBER){
            visualizationTypeList.add(VisualizationType.LINE_CHART);
            visualizationTypeList.add(VisualizationType.BAR_CHART);
        }

        if (dataProfile.getColumnCount() == 2 && dataProfile.getFirstColumnKind() == ColumnKind.NUMBER && dataProfile.getSecondColumnKind() == ColumnKind.NUMBER) {

            visualizationTypeList.add(VisualizationType.BAR_CHART);
            visualizationTypeList.add(VisualizationType.PIE_CHART);
        }

        visualizationTypeList.add(VisualizationType.TABLE);
        return visualizationTypeList;
    }
}
