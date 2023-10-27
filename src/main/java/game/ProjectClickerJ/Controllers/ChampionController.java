package game.ProjectClickerJ.Controllers;


import game.ProjectClickerJ.Models.Champion;
import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.ObjectRepositories.ChampionRepository;
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
public class ChampionController {

    @Autowired
    PlayerRepository playerRepo;

    @Autowired
    ChampionRepository championRepo;

    @Autowired
    WeaponRepository weaponRepo;

    public void GetPlayer(Model model, HttpSession session) {
        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        Player playerInstance = player.get();
        model.addAttribute("player", playerInstance);
    }

    @GetMapping("/shopChampion")
    public String listChamps(Model model, HttpSession session) {

        if (Utils.IsNotLogin(session,  playerRepo)) {
            return "connexion";
        }

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
        GetPlayer(model, session);

        return "championTemplate";
    }


    @PostMapping("/shopChampion")
    @ResponseBody
    @Transactional
    public ResponseEntity<Map<String, String>> buyChampion(HttpSession session, @RequestBody Map<String, Long> payload) {

        Long currentPlayerId = (Long) session.getAttribute("player");
        Long championId = payload.get("championId");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        Optional<Champion> champion = championRepo.findById(championId);

        if (player.isEmpty() || champion.isEmpty()) {
            System.out.println("player not found");
            return new ResponseEntity<>(Map.of("status", "error", "message", "Player or champion not found"), HttpStatus.BAD_REQUEST);
        } else {
            Player playerInstance = player.get();
            Champion championInstance = champion.get();

            int moneyLeft = playerInstance.getGold() - championInstance.getPrix();
            if (moneyLeft >= 0 && playerInstance.getXp() >= championInstance.getXpUnlockable()) {
                playerInstance.setGold(moneyLeft);
                List<Champion> playerChampions = playerInstance.getInventoryChampion();
                playerChampions.add(championInstance);
                playerInstance.setInventoryChampion(playerChampions);

            } else {
                System.out.println("pas assez d'argent ou d'xp");
            }
            playerRepo.save(playerInstance);
        }

        return new ResponseEntity<>(Map.of("status", "success", "message", "Champion purchased successfully"), HttpStatus.OK);
    }

    /*@PostMapping("/shopChampion")
    @Transactional
    public String buyChampion(HttpSession session, Long championId) {

        if (Utils.IsNotLogin(session,  playerRepo)) {
            return "connexion";
        }

        Long currentPlayerId = (Long) session.getAttribute("player");
        Optional<Player> player = playerRepo.findById(currentPlayerId);
        Optional<Champion> champion = championRepo.findById(championId);

        if (player.isEmpty() || champion.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        } else {
            Player playerInstance = player.get();
            Champion championInstance = champion.get();


            List<Champion> playerChampions = playerInstance.getInventoryChampion();
            playerChampions.add(championInstance);
            playerInstance.setInventoryChampion(playerChampions);
            playerRepo.save(playerInstance);
        }

        return "redirect:/shopChampion";
    }*/
}

