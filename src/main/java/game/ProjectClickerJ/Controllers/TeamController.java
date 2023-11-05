package game.ProjectClickerJ.Controllers;


import game.ProjectClickerJ.Models.Champion;
import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.Models.Team;
import game.ProjectClickerJ.Models.Weapon;
import game.ProjectClickerJ.ObjectRepositories.ChampionRepository;
import game.ProjectClickerJ.ObjectRepositories.PlayerRepository;
import game.ProjectClickerJ.ObjectRepositories.TeamRepository;
import game.ProjectClickerJ.ObjectRepositories.WeaponRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import game.ProjectClickerJ.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class TeamController {

    private Boolean teamExist = false;

    @Autowired
    TeamRepository teamRepo;

    @Autowired
    ChampionRepository championRepo;

    @Autowired
    WeaponRepository weaponRepo;

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

    public void listTeams(Model model, HttpSession session) {
        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        List<Team> teamsOwned = player.get().getInventoryTeams();
        model.addAttribute("teamsOwned", teamsOwned);

    }

    @GetMapping("/teamTemplate")
    public String getTeamTemplatePage(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session,  playerRepo)) {
            return "redirect:/connexion";
        }

        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }
        Player playerInstance = player.get();

        List<Champion> championsOwned = player.get().getInventoryChampion();
        List<Weapon> weaponsOwned = player.get().getInventoryWeapon();

        model.addAttribute("championsOwned", championsOwned);
        model.addAttribute("weaponsOwned", weaponsOwned);
        model.addAttribute("player", playerInstance);

        listTeams(model, session);
        return "teamTemplate";
    }


    @GetMapping("/addTeam")
    public String listOwnedWeaponsChampions(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "redirect:/connexion";
        }
        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }
        Player playerInstance = player.get();

        List<Champion> championsOwned = player.get().getInventoryChampion();
        List<Weapon> weaponsOwned = player.get().getInventoryWeapon();

        model.addAttribute("championsOwned", championsOwned);
        model.addAttribute("weaponsOwned", weaponsOwned);
        model.addAttribute("player", playerInstance);
        return "addTeam";
    }

    @PostMapping("/addTeam")
    public String addTeam(String name, Long championId, Long weaponId, HttpSession session) {

        teamExist = false;

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

            Optional<Weapon> weapon = weaponRepo.findById(weaponId);
            if (weapon.isEmpty()) {
                System.out.println("weapon not found");
                throw new RuntimeException("weapon not found");
            }

            Optional<Champion> champion = championRepo.findById(championId);
            if (champion.isEmpty()) {
                System.out.println("champion not found");
                throw new RuntimeException("champion not found");
            }

            //Verif team existe deja

            List<Team> playerTeams = playerInstance.getInventoryTeams();
            for (Team team : playerTeams) {
                if ((team.getWeapon().getId().equals(weaponId) && team.getChampion().getId().equals(championId) )|| team.getName().equals(name)) {
                    System.out.println("team ou pseudo existe deja");
                    teamExist = true;
                }
            }


            if (!teamExist) {
                Team team = new Team();
                team.setName(name);
                team.setChampion(champion.get());
                team.setWeapon(weapon.get());

                teamRepo.save(team);


                playerTeams.add(team);
                playerInstance.setInventoryTeams(playerTeams);
                playerRepo.save(playerInstance);


            }
        }

        return "redirect:/teamTemplate";

    }

    @GetMapping("selectTeam")
    public String GetTeams(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "redirect:/connexion";
        }
        listTeams(model, session);
        return "selectTeam";
    }


    @PostMapping("/selectTeam")
    @ResponseBody
    public ResponseEntity<?> selectTeam(@RequestBody Map<String, Long> payload, HttpSession session) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        Long teamId = payload.get("teamId");

        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
        } else {
            Player playerInstance = player.get();

            Optional<Team> team = teamRepo.findById(teamId);
            if (team.isEmpty()) {
                System.out.println("team not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found");
            }

            playerInstance.setSelectedTeam(team.get());
            playerRepo.save(playerInstance);

            return ResponseEntity.ok("Team selected successfully");
        }
    }


    @GetMapping("modifyTeam")
    public String listTeams2(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "redirect:/connexion";
        }

        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        List<Team> teamsOwned = player.get().getInventoryTeams();
        model.addAttribute("teamsOwned", teamsOwned);


        //Champion et Weapon Owned
        List<Champion> championsOwned = player.get().getInventoryChampion();
        List<Weapon> weaponsOwned = player.get().getInventoryWeapon();

        model.addAttribute("championsOwned", championsOwned);
        model.addAttribute("weaponsOwned", weaponsOwned);

        return "modifyTeam";
    }

    @PatchMapping("modifyTeam")
    public String modifyTeam(Long championId, Long weaponId, Long teamId, HttpSession session) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "connexion";
        }

        Optional<Weapon> weapon = weaponRepo.findById(weaponId);
        if (weapon.isEmpty()) {
            System.out.println("weapon not found");
            throw new RuntimeException("weapon not found");
        }

        Optional<Champion> champion = championRepo.findById(championId);
        if (champion.isEmpty()) {
            System.out.println("champion not found");
            throw new RuntimeException("champion not found");
        }

        Optional<Team> team = teamRepo.findById(teamId);
        if (team.isEmpty()) {
            System.out.println("team not found");
            throw new RuntimeException("team not found");
        }

        team.get().setChampion(champion.get());
        team.get().setWeapon(weapon.get());

        teamRepo.save(team.get());


        return "redirect:/modifyTeam";
    }

    @GetMapping("deleteTeam")
    public String GetTeamsDelete(Model model, HttpSession session) {
        if (Utils.IsNotLogin(session, playerRepo)) {
            return "redirect:/connexion";
        }
        listTeams(model, session);
        return "deleteTeam";
    }

    @DeleteMapping("/deleteTeam")
    @ResponseBody
    public ResponseEntity<?> deleteTeam(@RequestBody Map<String, Long> payload, HttpSession session) {
        System.out.println("okkkkkkkkkkkkkkkkk");
        if (Utils.IsNotLogin(session, playerRepo)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        Long teamId = payload.get("teamId");

        Long currentPlayerId = (Long) session.getAttribute("player");
        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
        }

        Optional<Team> team = teamRepo.findById(teamId);
        if (team.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Team not found");
        }

        List<Team> inventoryTeams = player.get().getInventoryTeams();
        inventoryTeams.removeIf(t -> t.getId().equals(teamId));
        player.get().setInventoryTeams(inventoryTeams);

        teamRepo.delete(team.get());

        return ResponseEntity.ok("Team deleted successfully");
    }

}



