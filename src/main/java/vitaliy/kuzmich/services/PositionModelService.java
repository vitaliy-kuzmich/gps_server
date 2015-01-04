package vitaliy.kuzmich.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vitaliy.kuzmich.dao.PositionModelDAO;
import vitaliy.kuzmich.model.PositionModel;
import vitaliy.kuzmich.model.User;

import java.util.List;

@Service
public class PositionModelService {
    @Autowired
    PositionModelDAO dao;

    @Transactional
    public void addOrUpdatePos(PositionModel pos, User user) throws Exception {
        dao.save(pos, user);
    }

    @Transactional(readOnly = true)
    public List<PositionModel> getAll(User user) throws UsernameNotFoundException {
        return dao.getAll(user);
    }

    @Transactional(readOnly = true)
    public List<PositionModel> getByPeriod(User user, long beginPeriod, long endPeriod) {
        return dao.getByPeriod(user, beginPeriod, endPeriod);
    }
}
