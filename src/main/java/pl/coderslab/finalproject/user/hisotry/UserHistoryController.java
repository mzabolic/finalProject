package pl.coderslab.finalproject.user.hisotry;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.finalproject.stores.Store;
import pl.coderslab.finalproject.user.User;

@Controller
@RequiredArgsConstructor
@RequestMapping("/historySql")
public class UserHistoryController {
    private final UserHistoryRepository historyRepository;



    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "0") int page, HttpSession httpSession, Model model){
        User user = (User) httpSession.getAttribute("loggedUser");
        Page<UserHistory> sqls = historyRepository.findAllByUser(user,PageRequest.of(page,20));

        model.addAttribute("sqls",sqls);
        return "/query/list";
    }


}
