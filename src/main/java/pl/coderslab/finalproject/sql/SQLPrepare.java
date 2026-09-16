package pl.coderslab.finalproject.sql;

import org.springframework.stereotype.Component;

@Component
public class SQLPrepare {
    public String prepareSql(String unPrepared){
        int index = unPrepared.toUpperCase().indexOf("SELECT");
        if (index != -1) {
            unPrepared = unPrepared.substring(index);
        }
        unPrepared = unPrepared.replace(";","");
        unPrepared = unPrepared.replace("`","");
        return unPrepared;
    }
}
