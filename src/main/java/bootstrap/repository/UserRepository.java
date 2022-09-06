package bootstrap.repository;

import bootstrap.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "graph.User.roles")
    User getUserByEmail(String email);
}
