package pl.coderslab.finalproject.nlp;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.finalproject.nlp.conversationmemory.ConversationMemory;
import pl.coderslab.finalproject.nlp.conversationmemory.ConversationTurn;
import pl.coderslab.finalproject.sql.SQLPrepare;
import pl.coderslab.finalproject.sql.SQLvalidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class NlpQueryService {

    private final PromptCreatorService promptCreatorService;
    private final AiClient aiClient;
    private final SQLvalidator sqLvalidator;
    private final SQLPrepare sqlPrepare;
    private final ConversationMemory memory;

    public List<String> generateClaryficationQuestion(String userQuery, String sesionId) {
        String prompt = promptCreatorService.generateInitialPrompt(userQuery);
        memory.add(sesionId, "user", prompt);
        String clarifcationQuestion = aiClient.generateAiAnswer(prompt);

        int i = clarifcationQuestion.indexOf('-');
        if (i != -1) {
            clarifcationQuestion = clarifcationQuestion.substring(i + 1);
        }

        List<String> questions = new ArrayList<>();

        String[] split = clarifcationQuestion.split("-");
        questions = Arrays.stream(split).toList();
        for (int j = 0; j < questions.size(); j++) {
            memory.add(sesionId, "groqApi","Question " + j + ": " + questions.get(j));
        }
        //questions.forEach(s -> memory.add(sesionId, "groqApi", s));
        return questions;
    }

    public String generateSql(List<String> userQuery, String sesionId) {
        //userQuery.stream().forEach(s -> memory.add(sesionId, "user", s));

        for (int j = 0; j < userQuery.size(); j++) {
            memory.add(sesionId, "user","Answer for question " + j + ": " + userQuery.get(j));
        }
        String chatHistoryString = memory.printHistory(sesionId);
        log.info(chatHistoryString);
        String prompt = promptCreatorService.generatePrompt(chatHistoryString);
        String querry = aiClient.generateAiAnswer(prompt);
        String sqlReady = sqlPrepare.prepareSql(querry);
        memory.add(sesionId,"groqApi", sqlReady);
        log.info(querry);

        if (sqLvalidator.isValid(sqlReady)) {
            return sqlReady;
        } else {
            throw new IllegalArgumentException("Generated SQL is not valid");

        }
    }
}
