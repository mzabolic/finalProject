package pl.coderslab.finalproject.queryresoult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class QueryResult {
    private List<String> columns;
    private List<List<Object>> rows;

    @Override
    public String toString() {
        return "QueryResoult{" +
                "columns=" + columns +
                ", rows=" + rows +
                '}';
    }
}
