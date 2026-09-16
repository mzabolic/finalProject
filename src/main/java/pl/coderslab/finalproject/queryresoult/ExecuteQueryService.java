package pl.coderslab.finalproject.queryresoult;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExecuteQueryService {

    private final JdbcTemplate jdbcTemplate;

    public QueryResult execute(String sql) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);


        if (result.isEmpty()) {
            return new QueryResult(List.of(), List.of());
        } else {
            List<String> columns = new ArrayList<>(result.get(0).keySet());


            List<List<Object>> rows = new ArrayList<>();
            rows = result.stream().
                    map(mapResult -> columns.stream()
                            .map(columnName -> mapResult.get(columnName)).
                            toList())
                    .toList();
            return  new QueryResult(columns, rows);
        }
    }
}

