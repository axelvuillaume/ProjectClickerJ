package game.ProjectClickerJ.Controllers;

import game.ProjectClickerJ.Models.Champion;
import game.ProjectClickerJ.Models.Player;
import game.ProjectClickerJ.Models.Team;
import game.ProjectClickerJ.Models.Weapon;
import game.ProjectClickerJ.ObjectRepositories.ChampionRepository;
import game.ProjectClickerJ.ObjectRepositories.WeaponRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BDDInit {

    @Autowired
    ChampionRepository championRepo;

    @Autowired
    WeaponRepository weaponRepo;

    public Weapon createWeapon(String name, String description, String image, int bonus, int prix, int xp) {
        Weapon w = new Weapon();
        w.setName(name);
        w.setDescription(description);
        w.setImage("weapon/" + image + ".webp");
        w.setBonus(bonus);
        w.setPrix(prix);
        w.setXpUnlockable(xp);
        weaponRepo.save(w);
        return w;
    }

    public Champion createChampion(String name, String description, String image, int bonus, int prix, int xp) {
        Champion c = new Champion();
        c.setName(name);
        c.setDescription(description);
        c.setImage("champions/" + image + ".webp");
        c.setBonus(bonus);
        c.setPrix(prix);
        c.setXpUnlockable(xp);
        championRepo.save(c);
        return c;
    }

    @GetMapping("/initBDD")
    public String initBDD() {
        List<Weapon> weapons = weaponRepo.findAll();

        if (weapons.size() > 0) {
            return "redirect:/connexion";
        }

        createWeapon(
                "Epée Wotan",
                "une épée distribuée aux élèves de la S-Class Shcool",
                "wotan",
                1,
                0,
                0
        );
        createWeapon(
                "Ame Rouge - Vorpaline",
                "une arme qui a absorbée le sang d'un démon de rang C",
                "vorpaline",
                1,
                100,
                1
        );
        createWeapon(
                "Tranche écarlate",
                "une épée taillée par un étrange individu nomé Rakan Iglovich au style futuristique",
                "violet",
                2,
                120,
                2
        );
        createWeapon(
                "Vérité - Coupeuse de Mensonge",
                "cette arme fut utilisé dans le passé pour obtenir des réponses, d'où son nom",
                "verite",
                4,
                300,
                3
        );
        createWeapon(
                "Epée d'Uriel",
                "cette épée a été dérobé a uriel, une combattante connu du pays",
                "uriel",
                5,
                600,
                4
        );createWeapon(
                "Unity V",
                "5eme et derniere épée de la série Unity",
                "unite",
                8,
                1500,
                5
        );
        createWeapon(
                "Typhon C-27",
                "cette épée a été ramené d'un endroit inconnue par Rakan Iglovich",
                "typhon",
                8,
                1000,
                6
        );

        // --------------------------------------------------------- //
        // -------------------------Champion------------------------ //
        // --------------------------------------------------------- //
        createChampion(
                "Bronya",
                "une très bonne élève de la S-Class School",
                "bronya",
                0,
                0,
                0
        );
        createChampion(
                "Kiana",
                "une ancienne élève de la S-Class School qui est devenu une chasseuse de prime",
                "anniv",
                1,
                150,
                0
        );
        createChampion(
                "Mei",
                "une amie de Kiana",
                "mei",
                3,
                370,
                2
        );
        createChampion(
                "Alad",
                "un groupe de combattant qui se déplace en tapis magique",
                "aladin",
                5,
                1300,
                5
        );createChampion(
                "Astral Mei",
                "la forme astral de Mei",
                "astral",
                7,
                3000,
                6
        );
        createChampion(
                "Carole",
                "carole est une tueuse de monstre en formation",
                "carole",
                10,
                5000,
                7
        );




        return "redirect:/connexion";
    }
}
