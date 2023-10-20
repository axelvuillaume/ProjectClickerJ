package game.ProjectClickerJ.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/connexion")
    public String getConnexionPage(Model model) {
        return "connexion";
    }

    @GetMapping("/index")
    public String getIndexPage(Model model) {
        return "index";
    }

    @GetMapping("/test")
    public String getTestPage(Model model) {
        return "testComponent";
    }
}
