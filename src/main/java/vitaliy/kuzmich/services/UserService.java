package vitaliy.kuzmich.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitaliy.kuzmich.dao.UsersDao;
import vitaliy.kuzmich.gps.messages.mobile.pojo.Auth;
import vitaliy.kuzmich.model.User;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UsersDao usersDao;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  /*  @Autowired
    private PasswordEncoder passwordEncoder;*/


    /*
        @Transactional(readOnly = true)
        public User loadUserByUsername(final String s) throws UsernameNotFoundException {
            return users.findByUserName(s);
        }*/
    @Transactional(readOnly = true)
    public List<User> getAll() throws UsernameNotFoundException {
        return usersDao.getAll();
    }

    @Transactional(readOnly = true)
    public User getByLogin(String login) {
        return usersDao.getByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getByID(Long id) {
        return usersDao.get(id);
    }

    @Transactional
    public User addOrUpdateUser(User user) throws Exception {
        if (user.getPassword() != null)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        else throw new Exception("password empty!");

        return usersDao.saveUser(user);

    }

    @Transactional
    public void delete(Long id) {
        usersDao.remove(id);

    }

    @Transactional(readOnly = true)
    public boolean isValid(Auth auth) {
        return passwordEncoder.matches(auth.getPassword(), getByLogin(auth.getUsername()).getPassword());
    }
}
