package pl.coderslab.finalproject.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;




    @GetMapping("/save")
    public String save (Model model){
        model.addAttribute("user", new User());

        return "/user/register";
    }

    @PostMapping("/save")
    public String save(@Valid User user, BindingResult result,
                         RedirectAttributes ra, Model model){

        if (userRepository.existsByUserName(user.getUserName())) {
            result.rejectValue(
                    "userName",
                    "duplicate.userName",
                    "That UserName already exists"
            );
        }
        if (result.hasErrors()){

            return "/user/register";
        }
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );
        userRepository.save(user);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("loginDto", new LoginDto());
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto,
                        HttpSession session) {
        List<User> users =
                userRepository.findFirstByUserName(loginDto.getUserName());

        if(users.isEmpty()){
            return "redirect:/user/login?error";
        }

        User user = users.get(0);

        if (!passwordEncoder.matches(
                loginDto.getPassword(),
                user.getPassword())) {
            return "redirect:/user/login?error";

        }

        session.setAttribute("loggedUser", user);

        return "redirect:/dashboard";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/dashboard";
    }


}
