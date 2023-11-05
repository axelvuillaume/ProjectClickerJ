package game.ProjectClickerJ.ObjectRepositories;

import game.ProjectClickerJ.Models.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    Weapon findByName(String keyword);

}
