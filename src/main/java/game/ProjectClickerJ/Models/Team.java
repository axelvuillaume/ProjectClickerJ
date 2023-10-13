package game.ProjectClickerJ.Models;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Team {

    @Getter
    @ManyToOne
    private Player player;

    @OneToOne
    private Weapon weapon;

    @OneToOne
    private Champion champion;

    @Id
    @GeneratedValue
    private Long id;

    private String name;


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

    public void setPlayer(Player player) {
        this.player = player;
    }
}
