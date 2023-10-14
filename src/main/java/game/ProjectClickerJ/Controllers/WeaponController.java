package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.Models.Weapon;
import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.ObjectRepositories.WeaponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class WeaponController {
    @Autowired
    WeaponRepository weaponRepo;

    @Autowired
    PlayerRepository playerRepo;


    // Juste pour creer un nouveau weapon faut enlever apres je pense
    @GetMapping("/ioupiWeapon")
    public String ioupi() {
        Weapon weapon = new Weapon();
        weapon.setName("IoupiWeapon1");
        weapon.setBonus(10);
        weapon.setPrix(100);
        weapon.setXpUnlockable(100);
        weapon.setDescription("Ioupi est un weapon de l'équipe de l'équipe");
        weapon.setImage("ioupi.png");
        weaponRepo.save(weapon);

        return "index";
    }


    @GetMapping("/shopWeapon")
    public String listWeapons(Model model) {
        List<Weapon> weaponsNotOwned = weaponRepo.findAll();

        Optional<Player> player = playerRepo.findById(20L /*playerId*/);
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





}