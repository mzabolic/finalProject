package pl.coderslab.finalproject.nlp;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.finalproject.queryresoult.ExecuteQueryService;
import pl.coderslab.finalproject.queryresoult.QueryResponse;
import pl.coderslab.finalproject.queryresoult.QueryResponseService;
import pl.coderslab.finalproject.user.User;
import pl.coderslab.finalproject.user.hisotry.UserHistory;
import pl.coderslab.finalproject.user.hisotry.UserHistoryRepository;

import java.util.List;

@Controller
@RequestMapping("/nlp")
@RequiredArgsConstructor
public class NLPControler {

    private final NlpQueryService nlpQueryService;
    private final ExecuteQueryService executeQueryService;
    private final HttpSession httpSession;
    private final UserHistoryRepository historyRepository;
    private final QueryResponseService queryResponseService;


    @PostMapping("/query/sql")
    public String querySql(@RequestParam List<String> answers, Model model){



        try {
            String sql = nlpQueryService.generateSql(answers,httpSession.getId());
            model.addAttribute("sql", sql);
            User user = (User) httpSession.getAttribute("loggedUser");
            if(user != null) {
                UserHistory history = new UserHistory();
                history.setSqlText(sql);
                history.setUser(user);

                historyRepository.save(history);
            }
            //QueryResult queryResult = executeQueryService.execute(sql);
            QueryResponse queryResponse = queryResponseService.buildQueryResponse(sql);
            model.addAttribute("result", queryResponse);
            return "/query/result";
        }catch (Exception e){
            String exeptionMessage = e.getCause().toString();
            answers.add("System thre exeption" + exeptionMessage + "make a change to sql");
            String sql = nlpQueryService.generateSql(answers,httpSession.getId());
            model.addAttribute("sql", sql);
            User user = (User) httpSession.getAttribute("loggedUser");
            if(user != null) {
                UserHistory history = new UserHistory();
                history.setSqlText(sql);
                history.setUser(user);

                historyRepository.save(history);
            }
            QueryResponse queryResponse = queryResponseService.buildQueryResponse(sql);
            model.addAttribute("result", queryResponse);
            return "/query/result";
        }

    }

    @PostMapping("query/exec")
    public String exec(@RequestParam String sql,Model model){
        QueryResponse queryResponse = queryResponseService.buildQueryResponse(sql);
        model.addAttribute("result", queryResponse);
        model.addAttribute("sql", sql);
        return "/query/result";
    }



    @PostMapping("/query/claryfication")
    public String queryClaryfication(@RequestParam String userQuery, Model model){


        // return nlpQueryService.generateAiAnswer(userQuery);
       java.util.List<String> questions = nlpQueryService.generateClaryficationQuestion(userQuery,httpSession.getId());
       model.addAttribute("questions",questions);



        return "/query/questions";
    }

    @GetMapping("/query")
    public String showForm(){
        return "query/enterQuery";
    }
}
