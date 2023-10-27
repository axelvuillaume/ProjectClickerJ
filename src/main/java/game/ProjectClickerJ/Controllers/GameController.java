package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.Utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/game")
    public String getConnexionPage(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session,  playerRepository)) {
            return "game"; // change back to connexion
        }
        return "game";
    }
}
