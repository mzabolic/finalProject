package pl.coderslab.finalproject.dataprofila;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class DataProfile {
    private int columnCount;

    private int rowCount;

    private ColumnKind firstColumnKind;

    private ColumnKind secondColumnKind;
}
