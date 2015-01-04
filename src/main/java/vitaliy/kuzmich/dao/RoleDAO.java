package vitaliy.kuzmich.dao;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import vitaliy.kuzmich.model.Role;

import java.util.List;

@Repository
public class RoleDAO extends GenericDao<Role, Long> {
    public RoleDAO() {
        super(Role.class);
    }

    public Role getByName(String name) {
        List roles = getSession().getNamedQuery(Role.FIND_ROLE_BY_NAME).setString("authority", name).list();
        //createCriteria(Role.class).add(Restrictions.eq("authority", name)).list();
        if (roles == null || roles.isEmpty()) {
            throw new UsernameNotFoundException("user '" + name + "' not found...");
        } else {
            return (Role) roles.get(0);
        }
    }
}
