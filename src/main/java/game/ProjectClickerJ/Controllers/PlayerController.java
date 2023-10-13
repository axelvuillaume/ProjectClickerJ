package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Champion;
import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.ObjectRepositories.ChampionRepository;
import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PlayerController {
    @Autowired
    PlayerRepository playerRepo;

    @Autowired
    ChampionRepository championRepo;

    @GetMapping("/ioupiPlayer")
    public String ioupi() {
        Player player = new Player();
        player.setPseudo("IoupiPlayer");
        player.setXp(100);
        player.setGold(100);
        player.setProfileImage("ioupi.png");
        playerRepo.save(player);
        return "index";
    }


    @PostMapping("/shopChampion")
    @Transactional
    public String buyChampion(HttpSession session, Long championId) {
        // Long playerId = (Long) session.getAttribute("playerId");
        Optional<Player> player = playerRepo.findById(1L /*playerId*/);
        Optional<Champion> champion = championRepo.findById(championId);

        if (player.isEmpty() || champion.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        } else {
            Player playerInstance = player.get();
            Champion championInstance = champion.get();

            playerInstance.getInventoryChampion().add(championInstance);
            playerRepo.save(playerInstance);
        }

        return "shopChampion";
    }

}


