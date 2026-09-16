package pl.coderslab.finalproject.nlp.conversationmemory;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConversationMemory {
    private Map<String, List<ConversationTurn>> history = new ConcurrentHashMap<>();

    public void add(String id, String role, String content) {
        List<ConversationTurn> conversationTurns = history.get(id);
        if (conversationTurns == null) {
            conversationTurns = new ArrayList<>();
            history.put(id, conversationTurns);
        }
        conversationTurns.add(new ConversationTurn(role, content));
    }

    public List<ConversationTurn> get(String id) {
        return history.getOrDefault(id, Collections.emptyList());
    }

    public void clearSesion(String id) {
        history.remove(id);
    }

    public String printHistory(String id) {
        List<ConversationTurn> conversationTurns = history.getOrDefault(id, Collections.emptyList());
        StringBuilder stringBuilder = new StringBuilder();
        for (ConversationTurn conversationTurn : conversationTurns) {
            stringBuilder.append(conversationTurn.print() + "\n");
        }
        return stringBuilder.toString();
    }
}
