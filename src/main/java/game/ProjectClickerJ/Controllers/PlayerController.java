package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Champion;
import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.Models.Weapon;
import game.ProjectClickerJ.ObjectRepositories.ChampionRepository;
import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.ObjectRepositories.WeaponRepository;
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

    @Autowired
    WeaponRepository weaponRepo;

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




    @PostMapping("/shopWeapon")
    @Transactional
    public String buyWeapon(HttpSession session, Long weaponId) {
        // Long playerId = (Long) session.getAttribute("playerId");
        Optional<Player> player = playerRepo.findById(20L /*playerId*/);

        Optional<Weapon> weapon = weaponRepo.findById(weaponId);
        if (player.isEmpty() || weapon.isEmpty()) {
            System.out.println("player or champion not found");
            throw new RuntimeException("player or champion not found");
        } else {
            Player playerInstance = player.get();
            Weapon weaponInstance = weapon.get();

            playerInstance.getInventoryWeapon().add(weaponInstance);
            playerRepo.save(playerInstance);
        }

        return "redirect:/shopChampion";
    }






}


