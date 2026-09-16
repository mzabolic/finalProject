package pl.coderslab.finalproject.nlp;

import java.util.List;

public class GroqResponseDTO {
    public record GroqResponse(List<Choice> choices) {
    }

    public record Choice(Message message) {
    }

    public record Message(String content) {
    }
}
