package pl.coderslab.finalproject;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.coderslab.finalproject.nlp.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class Tester implements CommandLineRunner {
    public final SchemaDescriptionService schemaDescriptionService;
    public final GrogClient grogClient;
    private final PromptCreatorService promptCreatorService;
    private final NlpQueryService nlpQueryService;
    @Override
    public void run(String... args) throws Exception {
//       log.info(schemaDescriptionService.getSchemaDescription());
//        log.info(openAiClient.generateAiAnswer("podaj najlpeszych 10 sklopow wedlug sprzedazy"));
    //log.info(nlpQueryService.generateClaryficationQuestion("Podaj 10 sklepow z najelpsza sprzedaza w swieta", "abc").toString());
    }
}
