package game.ProjectClickerJ.Models;

import jakarta.persistence.*;
import lombok.Getter;


@Getter
@Entity
public class Team {

    @ManyToOne
    private Weapon weapon;

    @ManyToOne
    private Champion champion;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Team(Champion championDefault, Weapon weaponDefault, String name) {
        this.setWeapon(weaponDefault);
        this.setChampion(championDefault);
        this.setName(name);
    }

    public Team() {

    }


    public void setName(String name) {
        this.name = name;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }


    public void setId(Long id) {
        this.id = id;
    }

}
