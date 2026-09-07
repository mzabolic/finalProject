package pl.coderslab.finalproject;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.coderslab.finalproject.analytics.AnalyticsService;
import pl.coderslab.finalproject.nlp.OpenAiClient;
import pl.coderslab.finalproject.nlp.SchemaDescriptionService;
import pl.coderslab.finalproject.nlp.UnsafeRestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class Tester implements CommandLineRunner {
    public final SchemaDescriptionService schemaDescriptionService;
    public final OpenAiClient openAiClient;
    @Override
    public void run(String... args) throws Exception {
//       log.info(schemaDescriptionService.getSchemaDescription());
//        log.info(openAiClient.generateSql("podaj najlpeszych 10 sklopow wedlug sprzedazy"));

     String response = openAiClient.generateSql("znajdz 10 sklepow z najlepsza sprzedaza");
        log.info(response);
    }
}
