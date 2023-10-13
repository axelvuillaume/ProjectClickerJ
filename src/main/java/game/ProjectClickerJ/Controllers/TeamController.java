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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/addTeam")
    public String listTeams(Model model) {
        List<Champion> champions = championRepo.findAll();
        model.addAttribute("champions",champions);
        List<Weapon> weapons = weaponRepo.findAll();
        model.addAttribute("weapons",weapons);

        return "addTeam";
    }

    @PostMapping("/addTeam")
    public String addTeam(String name, Long championId, Long weaponId, HttpSession session) {

        Long playerId = (Long) session.getAttribute("playerId");
        if(playerId != null) {
            System.out.println("weapon not found");
            throw new RuntimeException("weapon not found");
        }

        Optional<Weapon> weapon = weaponRepo.findById(weaponId);
        if(weapon.isEmpty()) {
            System.out.println("weapon not found");
            throw new RuntimeException("weapon not found");
        }

        Optional<Champion> champion = championRepo.findById(championId);
        if(champion.isEmpty()) {
            System.out.println("champion not found");
            throw new RuntimeException("champion not found");
        }

        Team team = new Team();
        team.setName(name);
        team.setChampion(champion.get());
        // team.setPlayer(playerId.get()); marche pas ..
        team.setWeapon(weapon.get());


        teamRepo.save(team);
        return "redirect:/addTeam";
    }
}
