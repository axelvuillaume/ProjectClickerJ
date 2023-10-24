package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.Utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class PageTemplateController {
    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/weaponTemplate")
    public String getConnexionPage(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session,  playerRepository)) {
            return "connexion";
        }
        return "weaponTemplate";
    }

    @GetMapping("/championTemplate")
    public String getConnextionPage(Model model, HttpSession session){
        if (Utils.IsNotLogin(session,  playerRepository)) {
            return "connexion";
        }
        return "championTemplate";
    }
}

