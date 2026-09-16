package pl.coderslab.finalproject.nlp.conversationmemory;

public record ConversationTurn(String role, String content) {
    public String print() {
        return role + " : " + content;
    }
}
