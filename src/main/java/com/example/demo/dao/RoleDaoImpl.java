package com.example.demo.dao;

import com.example.demo.model.Role;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleById(int id) {
        Session session = entityManager.unwrap(Session.class);
        Role role = (Role) session.createQuery("FROM Role where id =: roleId")
                .setParameter("roleId", id)
                .uniqueResult();
        session.close();
        return role;
    }

    @Override
    public List <Role> getRolesByName(Set <Integer>ids) {
        return   entityManager
                .createQuery("from Role role where role.id in (:ids)", Role.class)
                .setParameter("ids", ids).getResultList();
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("from Role").getResultList();
    }


}
