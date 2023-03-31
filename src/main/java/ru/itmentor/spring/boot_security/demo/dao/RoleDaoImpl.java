package ru.itmentor.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRole(Role user) {
        entityManager.persist(user);
    }

    public Role findByIdRole(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public List<Role> listRoles() {
        return entityManager.createQuery("select role from Role role", Role.class).getResultList();
    }

    public Role findByName(String name) {
        return entityManager.createQuery("select role from Role role where role.role = :id", Role.class)
                .setParameter("id", name)
                .getResultList().stream().findAny().orElse(null);
    }

    public List<Role> listByName(List<String> name) {
        return  entityManager.createQuery("select role from Role role where role.role in (:id)", Role.class)
                .setParameter("id", name)
                .getResultList();
    }

    @Override
    public List<Role> getByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery("select role from Role role where role.role = :id", Role.class);
        query.setParameter("id", name);
        return query.getResultStream().collect(Collectors.toList());
    }
}
