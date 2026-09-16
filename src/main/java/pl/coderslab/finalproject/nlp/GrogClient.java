package pl.coderslab.finalproject.nlp;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Slf4j
@Service
@RequiredArgsConstructor
public class GrogClient implements AiClient {
    @Value("${grog.api.key}")
    private String apiKey;


    @Override
    public String generateAiAnswer(String promptToLLM) {

        try {
            RestTemplate restTemplate = UnsafeRestTemplate.create();
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.setBearerAuth(apiKey);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);

            String body = """
                    {
                    "model": "qwen/qwen3.8-27b",
                    "messages": [
                    {
                    "role": "user",
                    "content": "%s"
                    }
                    ]
                    }
                    """.formatted(promptToLLM);

            HttpEntity<String> request = new HttpEntity<>(body, httpHeaders);

            ResponseEntity<GroqResponseDTO.GroqResponse> response =
                    restTemplate.postForEntity(
                            "https://api.groq.com/openai/v1/chat/completions",
                            request,
                            GroqResponseDTO.GroqResponse.class
                    );

            String content = response.getBody().choices().get(0).message().content();

            return content;
        } catch (Exception e) {
            log.info("failed connection to llm");
            e.printStackTrace();
            return null;
        }


    }
}
