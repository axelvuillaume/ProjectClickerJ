package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Champion;
import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.Models.Weapon;
import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.ObjectRepositories.WeaponRepository;
import game.ProjectClickerJ.Utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
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

        Player playerInstance = player.get();

        List<Weapon> weaponsOwned = player.get().getInventoryWeapon();

        weaponsNotOwned.removeAll(weaponsOwned);
        model.addAttribute("weaponsOwned", weaponsOwned);
        model.addAttribute("weaponsNotOwned", weaponsNotOwned);
        model.addAttribute("player", playerInstance);

        return "weaponTemplate";
    }

    @PostMapping("/shopWeapon")
    @ResponseBody
    @Transactional
    public ResponseEntity<Map<String, String>> buyWeapon(HttpSession session, @RequestBody Map<String, Long> payload) {

        Long currentPlayerId = (Long) session.getAttribute("player");
        Long weaponId = payload.get("weaponId");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        Optional<Weapon> weapon = weaponRepo.findById(weaponId);

        if (player.isEmpty() || weapon.isEmpty()) {
            System.out.println("player not found");
            return new ResponseEntity<>(Map.of("status", "error", "message", "Player or weapon not found"), HttpStatus.BAD_REQUEST);
        } else {
            Player playerInstance = player.get();
            Weapon weaponInstance = weapon.get();


            int moneyLeft = playerInstance.getGold() - weaponInstance.getPrix();
            if (moneyLeft >= 0 && playerInstance.getXp() >= weaponInstance.getXpUnlockable()) {
                List<Weapon> playerWeapons = playerInstance.getInventoryWeapon();
                playerWeapons.add(weaponInstance);
                playerInstance.setInventoryWeapon(playerWeapons);
                playerInstance.setGold(moneyLeft);

            } else {
                System.out.println("pas assez d'argent ou d'xp");
            }
            playerRepo.save(playerInstance);
        }

        return new ResponseEntity<>(Map.of("status", "success", "message", "Champion purchased successfully"), HttpStatus.OK);
    }

    /*@PostMapping("/shopWeapon")
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
    }*/



}