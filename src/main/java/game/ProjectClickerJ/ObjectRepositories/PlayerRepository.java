package game.ProjectClickerJ.ObjectRepositories;

import game.ProjectClickerJ.Models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player,Long> {
}
