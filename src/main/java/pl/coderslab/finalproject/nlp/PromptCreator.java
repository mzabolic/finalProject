package pl.coderslab.finalproject.nlp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Setter
@Getter
@RequiredArgsConstructor
@Slf4j
@Service
public class PromptCreator {
    private final SchemaDescriptionService schemaDescriptionService;
    private static String initial = "You are an expert on SQL and databases, " +
            "you generate sql query based on schema of data set and usser native language instructions. The schema is bresented bellow: ";

    private static String rules = "Rules:\n" +
            "- Use only tables from schema\n" +
            "- Use only columns from schema\n" +
            "- Return only SQL\n" +
            "- Do not explain anything\n"+
            "You may generate only SELECT statements.\n" +
            "Forbidden:\n" +
            "- INSERT\n" +
            "- UPDATE\n" +
            "- DELETE\n" +
            "- DROP\n" +
            "- ALTER\n" +
            "- TRUNCATE\n" +
            "Return only executable SQL.";



    public String generatePrompt(String usserQuery){
        StringBuilder stringBuilder = new StringBuilder();
        try {stringBuilder.append(initial)
                .append(schemaDescriptionService.getSchemaDescription())
                .append(rules)
                .append("Generate an SQL querry based on this usser native language question: ")
                .append("\n")
                .append(usserQuery)
                .append("\n");
                return stringBuilder.toString();

        }
        catch (Exception e){
            log.info("error during loding schema of dataset");
            return "";
        }

    }
}
