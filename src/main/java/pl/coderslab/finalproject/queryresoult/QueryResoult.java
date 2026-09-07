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
public class QueryResoult {
    private List<String> columns;
    private List<List<String>> rows;
}
