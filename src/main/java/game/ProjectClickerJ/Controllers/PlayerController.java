package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Champion;
import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.Models.Weapon;
import game.ProjectClickerJ.ObjectRepositories.ChampionRepository;
import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.ObjectRepositories.WeaponRepository;
import game.ProjectClickerJ.Utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import java.util.Optional;

@Controller
public class PlayerController {

    @Autowired
    PlayerRepository playerRepo;

    @PostMapping("/login")
    public String login(HttpSession session, String pseudo) {
        Player player = playerRepo.findByPseudo(pseudo);
        if (player == null) {
            player = new Player();
            player.setPseudo(pseudo);
            player.setXp(0);
            player.setGold(0);
            playerRepo.save(player);
        }
        session.setAttribute("player", player.getId());
        return "redirect:/index";

    }

    @GetMapping("settings")
    public String GetTeams(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session,  playerRepo)) {
            return "connexion";
        }


        return "settings";
    }

    @PostMapping("/logout")
    public String logout(SessionStatus sessionStatus, HttpSession session) {
        Long currentPlayerId = (Long) session.getAttribute("player");

        if (currentPlayerId != null) {
            session.invalidate();

        }

        return "redirect:/connexion";

    }


}


