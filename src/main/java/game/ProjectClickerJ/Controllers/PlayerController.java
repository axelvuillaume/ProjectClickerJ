package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.Utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Optional;

@Controller
public class PlayerController {


    @Autowired
    PlayerRepository playerRepo;

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


    @PostMapping("/login")
    public String login(HttpSession session, String pseudo) {
        Player player = playerRepo.findByPseudo(pseudo);
        if (player == null) {
            player = new Player();
            player.setProfileImage("pg.png");
            player.setPseudo(pseudo);
            player.setXp(0);
            player.setGold(0);
            playerRepo.save(player);
        }
        session.setAttribute("player", player.getId());
        return "redirect:/";
    }

    @GetMapping("settings")
    public String Getsettings(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "connexion";
        }
        GetPlayer(model, session);
        return "settings";
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        Long currentPlayerId = (Long) session.getAttribute("player");
        if (currentPlayerId != null) {
            session.invalidate();
        }
        return "redirect:/connexion";
    }


    @PostMapping("/changePseudo")
    public String changePseudo(HttpSession session, String name) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "connexion";
        }

        Long currentPlayerId = (Long) session.getAttribute("player");
        Optional<Player> player = playerRepo.findById(currentPlayerId);

        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        } else {
            Player playerInstance = player.get();
            if (playerInstance.getPseudo().equals(name)) {
                return "redirect:/settings";
            } else {
                playerInstance.setPseudo(name);
                playerRepo.save(playerInstance);
                return "redirect:/settings";
            }
        }
    }

    @PostMapping("/changePP")
    public String changePP(HttpSession session, String PP) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "connexion";
        }

        Long currentPlayerId = (Long) session.getAttribute("player");
        Optional<Player> player = playerRepo.findById(currentPlayerId);

        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        } else {
            Player playerInstance = player.get();
            if (playerInstance.getProfileImage() == null) {
                playerInstance.setProfileImage(PP);
                playerRepo.save(playerInstance);
                return "redirect:/settings";

            } else if (playerInstance.getProfileImage().equals(PP)) {
                return "redirect:/settings";
            } else {
                playerInstance.setProfileImage(PP);
                playerRepo.save(playerInstance);
                return "redirect:/settings";
            }
        }
    }

    @PatchMapping("/addGold")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addGold(HttpSession session) {

        Long currentPlayerId = (Long) session.getAttribute("player");
        Optional<Player> player = playerRepo.findById(currentPlayerId);

        if (player.isEmpty()) {
            System.out.println("player not found");
            return new ResponseEntity<>(Map.of("status", "error", "message", "player not found"), HttpStatus.BAD_REQUEST);
        } else {
            Player playerInstance = player.get();

            int XpTotal = playerInstance.getXp();
            playerInstance.setXp(XpTotal + 1);

            int goldTotal = playerInstance.getGold();
            playerInstance.setGold(goldTotal + 5);
            playerRepo.save(playerInstance);

            return  new ResponseEntity<>(Map.of("status", "success", "message", "gold claimed successfully", "newGoldValue", playerInstance.getGold()), HttpStatus.OK);
        }

    }


    @GetMapping("testAddGold")
    public String GetTestAddGold(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "connexion";
        }
        GetPlayer(model, session);
        return "testAddGold";
    }
}