package pl.coderslab.finalproject.charts;

import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.queryresoult.QueryResult;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChartDataConverter {

    public ChartData convertToChartData(QueryResult queryResult) {
        List<String> labels = new ArrayList<>();
        List<DataSet> dataSets = new ArrayList<>();

        List<String> columns = queryResult.getColumns();

        for (int i = 0; i < queryResult.getRows().size(); i++) {
            labels.add(String.valueOf( queryResult.getRows().get(i).get(0)));
        }



            for (int i = 1; i < columns.size(); i++) {
                String label = (String) columns.get(i);
                List<BigDecimal> values= new ArrayList<>();
                for (int j = 0; j < queryResult.getRows().size(); j++) {
                    Object value = queryResult.getRows()
                            .get(j)
                            .get(i);

                    values.add(
                            new BigDecimal(
                                    value.toString()
                            )
                    );

            }
                dataSets.add(new DataSet(label, values));

        }


        return new ChartData(labels, dataSets);

    }
}
