package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.Utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @Autowired
    PlayerRepository playerRepository;
    @GetMapping("/connexion")
    public String getConnexionPage(Model model, HttpSession session) {
        if (!Utils.IsNotLogin(session,  playerRepository)) {
            return "index";
        }
        return "connexion";
    }

    @GetMapping("/index")
    public String getIndexPage(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session,  playerRepository)) {
            return "connexion";
        }
        return "index";
    }

    @GetMapping("/test")
    public String getTestPage(Model model, HttpSession session) {
        return "testComponent";
    }
}
