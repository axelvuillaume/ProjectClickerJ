package game.ProjectClickerJ.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Weapon {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private int bonus;
    @Column
    private String description;
    @Column
    private int prix;
    @Column
    private int xpUnlockable;
    @Column
    private String Image;


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setXpUnlockable(int xpUnlockable) {
        this.xpUnlockable = xpUnlockable;
    }

    public void setImage(String image) {
        Image = image;
    }
}
