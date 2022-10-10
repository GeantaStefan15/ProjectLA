package Link.example.Project.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.GeneratedValue;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserSession userSession;

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @PostMapping("/register-action")
    public ModelAndView registerAction(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
                                       @RequestParam("email") String email,
                                       @RequestParam("phoneNumber") String phoneNumber,
                                       @RequestParam("password1") String password1,
                                       @RequestParam("password2") String password2,
                                       @RequestParam("age") int age) {
        ModelAndView modelAndView = new ModelAndView("register");
        if (!password1.equals(password2)) {
            modelAndView.addObject("message", "Wrong passwords");
            return modelAndView;
        }

        try {
            userService.checkAndAddUser(email, password1, firstName, lastName, phoneNumber, age);
        } catch (UserException e) {
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;
        }
        return new ModelAndView("redirect:/index.html");
    }

    @PostMapping("/login-action")
    public ModelAndView loginAction(@RequestParam("email") String email, @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView("index");
        try {
            userService.checkAndLoginUser(email, password);
        } catch (UserException e) {
            modelAndView.addObject("message", e.getMessage());
            return modelAndView;

        }
        userSession.setId(userService.getUserId(email));
        return new ModelAndView("redirect:/dashboard");
    }
    @GetMapping("/dashboard")
    public ModelAndView dashboard(){
        ModelAndView modelAndView = new ModelAndView("dashboard");
        if (userSession.getId() == 0) {
            return new ModelAndView("index");
        }
        return modelAndView;};

}


