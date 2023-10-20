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
    @ManyToMany
    private List<Weapon> InventoryWeapon;
    @ManyToMany
    private List<Champion> InventoryChampion;
    @ManyToMany
    private List<Team> InventoryTeams;
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

    public void setSelectedTeam(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }

    public void setInventoryTeams(List<Team> inventoryTeams) {
        InventoryTeams = inventoryTeams;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

}
