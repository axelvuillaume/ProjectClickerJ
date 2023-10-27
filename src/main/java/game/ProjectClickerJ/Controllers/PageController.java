package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.Utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

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
        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepository.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        Player playerInstance = player.get();
        model.addAttribute("player", playerInstance);
        return "index";
    }

    @GetMapping("/test")
    public String getTestPage(Model model, HttpSession session) {
        return "testComponent";
    }
}
