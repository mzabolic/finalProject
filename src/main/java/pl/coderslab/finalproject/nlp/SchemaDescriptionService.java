package pl.coderslab.finalproject.nlp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.holidayevent.HolidayEvent;
import pl.coderslab.finalproject.stores.Store;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SchemaDescriptionService {
    private final DataSource dataSource;

    public String getSchemaDescription() throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String schemaName = connection.getCatalog();

            ResultSet tables = metaData.getTables(
                    schemaName,
                    null,
                    "%",
                    new String[]{"TABLE"}
            );



            stringBuilder.append("\n");
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");

                if ("user".equalsIgnoreCase(tableName)
                        || "user_history".equalsIgnoreCase(tableName)) {
                    continue;
                }

               Set<String> primaryKeys = new HashSet<>();
                ResultSet pk = metaData.getPrimaryKeys(
                        schemaName,
                        null,
                        tableName);

                while (pk.next()){
                    primaryKeys.add(pk.getString("COLUMN_NAME"));
                }
                Map<String,String> foreignKeys = new HashMap<>();
                ResultSet fk = metaData.getImportedKeys(
                        schemaName,
                        null,
                        tableName);
                while (fk.next()){
                    foreignKeys.put(
                            fk.getString("FKCOLUMN_NAME"),
                            fk.getString("PKTABLE_NAME") + "." +
                            fk.getString("PKCOLUMN_NAME"));
                }

                stringBuilder.append("TABLE ")
                        .append(tableName)
                        .append("\n");
                ResultSet columns = metaData.getColumns(
                        schemaName,
                        null,
                        tableName,
                        "%"
                );

                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String type = columns.getString("TYPE_NAME");

                    stringBuilder.append(" COLUMN ")
                            .append(columnName)
                            .append(" ")
                            .append(type)
                            .append(" ");

                    if (primaryKeys.contains(columnName)){
                        stringBuilder.append("PRIMARY_KEY ");
                    }
                    if (foreignKeys.keySet().contains(columnName)){
                        stringBuilder.append("FOREIGN KEY -> ")
                                .append(foreignKeys.get(columnName))
                                .append(" ");
                    }
                }
                stringBuilder.append("\n");

            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
//        Arrays.stream(HolidayEvent.class.getDeclaredFields()).toList().stream().
//                forEach(e-> stringBuilder.append(e.toString()+ "\n"));
//        return stringBuilder.toString();
//    }
    }
}
