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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class ChampionController {

    @Autowired
    PlayerRepository playerRepo;

    @Autowired
    ChampionRepository championRepo;

    @Autowired
    WeaponRepository weaponRepo;

    @GetMapping("/shopChampion")
    public String listChamps(Model model, HttpSession session) {
        List<Champion> championsNotOwned = championRepo.findAll();

        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        List<Champion> championsOwned = player.get().getInventoryChampion();

        championsNotOwned.removeAll(championsOwned);
        model.addAttribute("championsOwned", championsOwned);
        model.addAttribute("championsNotOwned", championsNotOwned);

        System.out.println(championsOwned);

        return "shopChampion";
    }


    @PostMapping("/buyChampion")
    @Transactional
    public String buyChampion(HttpSession session, Long championId) {

        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
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

