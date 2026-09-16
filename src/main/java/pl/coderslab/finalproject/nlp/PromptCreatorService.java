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
public class PromptCreatorService {
    private final SchemaDescriptionService schemaDescriptionService;
    private static String initial = "You are an expert on SQL and databases, " +
            "you generate sql query for MySQL, based on schema of data set and usser native language instructions. The schema is bresented bellow: ";

    private static String sqlInitial = "You are an expert on SQL and databases, " +
            "you generate sql query for MySQL, based on schema of data set and usser native language instructions. The history of your chat with a usser is bellow: ";

    private static String clarification = """
            # Requirements Clarification Stage
            
            Your task at this stage is NOT to generate SQL.
            
            Analyze the user's request together with the provided database schema and determine whether any additional information is needed before a SQL query can be created in a later stage.
            
            If the request is ambiguous, incomplete, or could be interpreted in multiple ways, ask clarification questions.
            
            You may ask about:
            - which columns should be returned,
            - which entities or tables are relevant,
            - filtering conditions,
            - date or time ranges,
            - aggregation requirements (SUM, AVG, COUNT, MIN, MAX),
            - grouping requirements,
            - sorting requirements,
            - result limits,
            - business terms that may have multiple meanings,
            - any other information required to build an accurate SQL query.
            
            Ask only questions that are necessary. Do not ask questions whose answers can be determined from the user's request or the database schema.
            
            Do not ask more than 3 questions at a time.
            
            If no clarification is needed, do not ask any questions.
            
            In that case, return exactly:
            
            NO_CLARIFICATION_NEEDED
            
            Do not add any explanation.
            
            Never generate SQL during this stage.
            
            The only allowed outputs are:
            
            CLARIFICATION_QUESTIONS:
            - Question 1
            - Question 2
            - Question 3
            
            or
            
            NO_CLARIFICATION_NEEDED""";

    private static String rules = "Rules for creating sqlQuery:\n" +
            "- Use only tables from schema\n" +
            "- Use only columns from schema\n" +
            "- Return only SQL\n" +
            "- Do not explain anything\n" +
            "You may generate only SELECT statements.\n" +
            "Forbidden:\n" +
            "- INSERT\n" +
            "- UPDATE\n" +
            "- DELETE\n" +
            "- DROP\n" +
            "- ALTER\n" +
            "- TRUNCATE\n" +
            "Return only executable SQL.";


    public String generatePrompt(String userQuery) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append(initial)
                   // .append(schemaDescriptionService.getSchemaDescription())

                    .append("\n")
                    .append(userQuery)
                    .append("\n")
                    .append(rules)
                    .append("\n");

            String escapedPrompt = stringBuilder.toString()
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "");
            return escapedPrompt;

        } catch (Exception e) {
            log.info("error during loding schema of dataset");
            return "";
        }

    }

    public String generateInitialPrompt(String userQuery){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append(initial)
                    .append(schemaDescriptionService.getSchemaDescription())
                    .append("\n")
                    .append(userQuery)
                    .append("\n")
                    .append(clarification)
                    .append("\n");

            String escapedPrompt = stringBuilder.toString()
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "");
            return escapedPrompt;

        } catch (Exception e) {
            log.info("error during loding schema of dataset");
            return "";
        }

    }
}
