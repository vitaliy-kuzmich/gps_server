package vitaliy.kuzmich.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import vitaliy.kuzmich.config.Const;
import vitaliy.kuzmich.model.PositionModel;
import vitaliy.kuzmich.model.User;

import java.util.List;

@Repository
public class UsersDao extends GenericDao<User, Long> {


    public UsersDao() {
        super(User.class);
    }

    public User getByLogin(String username) {
        List users = getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (User) users.get(0);
        }
    }

    public User saveUser(User user) {
        if (log.isDebugEnabled()) {
            log.debug("user's id: " + user.getId());
        }
        Session session = getSession();

        session.saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        SQLQuery q = session.createSQLQuery(Const.SQL_CREATE_POSITION_MODEL.replaceFirst(Const.KEY_TABLE_NAME, user.getUsername() + "_" + PositionModel.class.getSimpleName()));
        q.executeUpdate();
        getSession().flush();


        return user;
    }


    private void createUniquePositionTable(Session session, User user) {
        SQLQuery q = session.createSQLQuery(Const.SQL_CREATE_POSITION_MODEL.replaceFirst(Const.KEY_TABLE_NAME, user.getUsername() + "_" + PositionModel.class.getSimpleName()));
        q.executeUpdate();
    }

    private void dropUniquePositionTable(Session session, User user) {
        SQLQuery q = session.createSQLQuery("drop table " + user.getUsername() + "_" + PositionModel.class.getSimpleName());
        q.executeUpdate();
    }

    @Override
    public User save(User user) {
        User u = super.save(user);
        return u;
    }

    @Override
    public void remove(User object) {
        super.remove(object);
    }

    @Override
    public void remove(Long id) {
        super.remove(id);
    }
}
