package bootstrap.repository;

import bootstrap.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleByName(String name);
}
