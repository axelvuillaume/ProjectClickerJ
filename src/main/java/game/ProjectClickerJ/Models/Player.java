package game.ProjectClickerJ.Models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Player {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String pseudo;
    @Column
    private int gold;
    @Column
    private int xp;
    @OneToMany
    private List<Weapon> InventoryWeapon;
    @OneToMany
    private List<Champion> InventoryChampion;
    @OneToMany
    private List<Team> Teams;
    @ManyToOne
    private Team selectedTeam;

    private String ProfileImage;


    public void setId(Long id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setInventoryWeapon(List<Weapon> inventoryWeapon) {
        InventoryWeapon = inventoryWeapon;
    }

    public void setInventoryChampion(List<Champion> inventoryChampion) {
        InventoryChampion = inventoryChampion;
    }

    public void setTeams(List<Team> teams) {
        Teams = teams;
    }

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

}
