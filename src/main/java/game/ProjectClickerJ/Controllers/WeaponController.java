package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.Models.Weapon;
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

import java.util.List;
import java.util.Optional;

@Controller
public class WeaponController {
    @Autowired
    WeaponRepository weaponRepo;

    @Autowired
    PlayerRepository playerRepo;

    @GetMapping("/shopWeapon")
    public String listWeapons(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session,  playerRepo)) {
            return "connexion";
        }
        List<Weapon> weaponsNotOwned = weaponRepo.findAll();

        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        List<Weapon> weaponsOwned = player.get().getInventoryWeapon();

        weaponsNotOwned.removeAll(weaponsOwned);
        model.addAttribute("weaponsOwned", weaponsOwned);
        model.addAttribute("weaponsNotOwned", weaponsNotOwned);

        return "shopWeapon";
    }

    @PostMapping("/shopWeapon")
    @Transactional
    public String buyWeapon(HttpSession session, Long weaponId) {
        if (Utils.IsNotLogin(session,  playerRepo)) {
            return "connexion";
        }
        Long currentPlayerId = (Long) session.getAttribute("player");
        Optional<Player> player = playerRepo.findById(currentPlayerId);
        Optional<Weapon> weapon = weaponRepo.findById(weaponId);

        if (player.isEmpty() || weapon.isEmpty()) {
            System.out.println("player or champion not found");
            throw new RuntimeException("player or champion not found");
        } else {
            Player playerInstance = player.get();
            Weapon weaponInstance = weapon.get();

            List<Weapon> playerWeapons = playerInstance.getInventoryWeapon();
            playerWeapons.add(weaponInstance);
            playerInstance.setInventoryWeapon(playerWeapons);
            playerRepo.save(playerInstance);
        }

        return "redirect:/shopWeapon";
    }



}