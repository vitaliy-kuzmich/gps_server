package vitaliy.kuzmich.services;

import vitaliy.kuzmich.dao.RoleDAO;
import vitaliy.kuzmich.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleDAO roleDAO;

    @Transactional(readOnly = true)
    public List<Role> getAll() {
        return roleDAO.getAll();
    }
    @Transactional(readOnly = true)
    public Role getByName(String name) {
       return roleDAO.getByName(name);

    }
    @Transactional
    public void addOrUpdateUser(Role role) throws Exception {
        role.setId(0l);
        roleDAO.save(role);
    }


}
