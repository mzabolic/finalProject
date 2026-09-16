package pl.coderslab.finalproject.dashbord;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class DashboeardControler {

    @GetMapping("dashboard")
    public String navigation(){
        return "/dashboard/navigation";
    }
}
