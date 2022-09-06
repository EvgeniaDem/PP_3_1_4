package bootstrap.repository;

import bootstrap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
}
