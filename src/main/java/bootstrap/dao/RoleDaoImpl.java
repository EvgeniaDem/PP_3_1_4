package bootstrap.dao;

import bootstrap.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getByName(String name) {
        try {
            return entityManager.createQuery("select r from Role r where r.name = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public List<Role> getAll() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }
}