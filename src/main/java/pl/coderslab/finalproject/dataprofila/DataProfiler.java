package pl.coderslab.finalproject.dataprofila;

import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.queryresoult.QueryResult;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class DataProfiler {
    public DataProfile profile(QueryResult queryResult){

        DataProfile dataProfile = new DataProfile();
        dataProfile.setColumnCount(queryResult.getColumns().size());
        dataProfile.setRowCount(queryResult.getRows().size());


        if (!queryResult.getRows().isEmpty()) {

            List<Object> firstRow =
                    queryResult.getRows().get(0);

            if (!firstRow.isEmpty()) {
                dataProfile.setFirstColumnKind(
                        detectType(firstRow.get(0)));
            }

            if (firstRow.size() > 1) {
                dataProfile.setSecondColumnKind(
                        detectType(firstRow.get(1)));
            }
        }
        return dataProfile;

    }

    private ColumnKind detectType(Object value) {

        if (value instanceof String text) {
            if(text.matches("\\d{4}-\\d{2}-\\d{2}")){

                return ColumnKind.DATE;

            }
            return ColumnKind.TEXT;
        }

        if (value instanceof Number) {
            return ColumnKind.NUMBER;
        }

        if (value instanceof java.sql.Date
                || value instanceof java.util.Date
                || value instanceof LocalDate
                || value instanceof LocalDateTime
                || value instanceof Timestamp) {

            return ColumnKind.DATE;
        }

        if (value instanceof Boolean) {
            return ColumnKind.BOOLEAN;
        }

        return ColumnKind.UNKNOWN;
    }
}
