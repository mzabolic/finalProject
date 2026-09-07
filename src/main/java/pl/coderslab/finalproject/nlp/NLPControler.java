package pl.coderslab.finalproject.nlp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.finalproject.sql.SQLvalidator;

@RestController
@RequestMapping("/nlp")
@RequiredArgsConstructor
public class NLPControler {
    private final PromptCreator promptCreator;
    private final AiClient aiClient;
    private final SQLvalidator sqLvalidator;

    @PostMapping("/query")
    public String query(@RequestBody String usserQuery){
        String prompt = promptCreator.generatePrompt(usserQuery);
        String querry = aiClient.generateSql(prompt);

        if (sqLvalidator.isValid(querry))
        {
            return querry;
        }
        else {
            return null;
        }
    }
}
