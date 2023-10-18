package game.ProjectClickerJ.ObjectRepositories;

import game.ProjectClickerJ.Models.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChampionRepository extends JpaRepository<Champion, Long> {
}
