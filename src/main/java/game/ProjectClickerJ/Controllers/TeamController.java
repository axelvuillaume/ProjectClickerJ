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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import game.ProjectClickerJ.Utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TeamController {
    @Autowired
    TeamRepository teamRepo;

    @Autowired
    ChampionRepository championRepo;

    @Autowired
    WeaponRepository weaponRepo;

    @Autowired
    PlayerRepository playerRepo;

    public void listTeams(Model model, HttpSession session) {
        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        List<Team> teamsOwned = player.get().getInventoryTeams();
        model.addAttribute("teamsOwned",teamsOwned);

    }



    @GetMapping("/addTeam")
    public String listOwnedWeaponsChampions(Model model, HttpSession session) {
        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        List<Champion> championsOwned = player.get().getInventoryChampion();
        List<Weapon> weaponsOwned = player.get().getInventoryWeapon();

        model.addAttribute("championsOwned",championsOwned);
        model.addAttribute("weaponsOwned",weaponsOwned);
        return "addTeam";
    }

    @PostMapping("/addTeam")
    public String addTeam(String name, Long championId, Long weaponId, HttpSession session) {
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

            Team team = new Team();
            team.setName(name);
            team.setChampion(champion.get());
            team.setWeapon(weapon.get());

            teamRepo.save(team);

            playerInstance.getInventoryTeams().add(team);
            playerRepo.save(playerInstance);
        }

        return "addTeam";

    }

    @GetMapping("selectTeam")
    public String GetTeams(Model model, HttpSession session) {
        listTeams(model,session);
        return "selectTeam";
    }


    @PostMapping("/selectTeam")
    public String selectTeam(Long teamId, HttpSession session) {
        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        } else {
            Player playerInstance = player.get();

            Optional<Team> team = teamRepo.findById(teamId);
            if (team.isEmpty()) {
                System.out.println("team not found");
                throw new RuntimeException("team not found");
            }

            playerInstance.setSelectedTeam(team.get());
            playerRepo.save(playerInstance);
        }

        return "selectTeam";
    }


    @GetMapping("modifyTeam")
    public String listTeams2(Model model, HttpSession session) {
        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        List<Team> teamsOwned = player.get().getInventoryTeams();
        model.addAttribute("teamsOwned",teamsOwned);


        //Champion et Weapon Owned
        List<Champion> championsOwned = player.get().getInventoryChampion();
        List<Weapon> weaponsOwned = player.get().getInventoryWeapon();

        model.addAttribute("championsOwned",championsOwned);
        model.addAttribute("weaponsOwned",weaponsOwned);

        return "modifyTeam";
    }

    @PatchMapping("modifyTeam")
    public String modifyTeam(Long championId, Long weaponId,Long teamId){

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
        listTeams(model,session);
        return "deleteTeam";
    }

    @DeleteMapping("deleteTeam")
    public String deleteTeam(Long teamId,HttpSession session){

        Long currentPlayerId = (Long) session.getAttribute("player");

        Optional<Player> player = playerRepo.findById(currentPlayerId);
        if (player.isEmpty()) {
            System.out.println("player not found");
            throw new RuntimeException("player not found");
        }

        Optional<Team> team = teamRepo.findById(teamId);
        if (team.isEmpty()) {
            System.out.println("team not found");
            throw new RuntimeException("team not found");
        }

        List<Team> inventoryTeams = player.get().getInventoryTeams();

        List<Team> teamsToKeep = new ArrayList<>();
        for (Team inventoryTeam : inventoryTeams) {
            if (!inventoryTeam.equals(team.get())) {
                teamsToKeep.add(inventoryTeam);
            }
        }

        player.get().setInventoryTeams(teamsToKeep);

        teamRepo.delete(team.get());

        return "deleteTeam";
    }

}



