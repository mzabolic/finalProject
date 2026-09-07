package pl.coderslab.finalproject.sql;

import org.springframework.stereotype.Component;

@Component
public class SQLvalidator {
    public boolean isValid(String querry){
        String normalized = querry.toLowerCase().trim();
        return normalized.startsWith("select");
    }

}
