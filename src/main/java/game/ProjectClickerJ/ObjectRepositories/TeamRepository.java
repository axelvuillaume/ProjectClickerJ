package game.ProjectClickerJ.ObjectRepositories;

import game.ProjectClickerJ.Models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
