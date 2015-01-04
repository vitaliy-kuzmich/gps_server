package vitaliy.kuzmich.config;

import vitaliy.kuzmich.dao.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;


/*@Component*/
public class UsersDetServ /*implements UserDetailsService*/ {
    @Autowired
    UsersDao usersDao;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersDao.getByLogin(username);
    }
}
