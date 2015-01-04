package vitaliy.kuzmich.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vitaliy.kuzmich.config.Const;
import vitaliy.kuzmich.model.PositionModel;
import vitaliy.kuzmich.model.User;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class PositionModelDAO extends GenericDao<PositionModel, Long> {

    @Autowired
    DataSource dataSource;

    public PositionModelDAO() {
        super(PositionModel.class);
    }


    @Override
    @Deprecated
    public List<PositionModel> getAll() {
        return null;
    }

    private String makeTableName(User user) {
        return user.getUsername() + "_" + PositionModel.class.getSimpleName();
    }

    public List<PositionModel> getAll(User user) {
        return null;
    }

    @Override
    @Deprecated
    public PositionModel get(Long id) {
        return null;
    }

    public PositionModel get(Long id, User user) {
        return null;
    }

    @Override
    @Deprecated
    public boolean exists(Long id) {
        return false;
    }

    @Override
    @Deprecated
    public PositionModel save(PositionModel pos) {
        return null;

    }

    public int save(PositionModel pos, User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
     /*   SQLQuery q = getSession().createSQLQuery(Const.SQL_INSERT_POSITION_MODEl.replaceFirst(Const.KEY_TABLE_NAME, makeTableName(user)));

        q.setParameter("accuracy", pos.getAccuracy())
                .setParameter("saveTime", pos.getSaveTime())
                .setParameter("altitude", pos.getAltitude())
                .setParameter("bearing", pos.getBearing())
                .setParameter("latitude", pos.getLatitude())
                .setParameter("longitude", pos.getLongitude())
                .setParameter("speed", pos.getSpeed());
        return q.executeUpdate();*/
        String sql = Const.SQL_INSERT_POSITION_MODEl.replaceFirst(Const.KEY_TABLE_NAME, makeTableName(user));

        jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.update(sql, new Object[]{pos.getSaveTime(), pos.getAccuracy(),
                pos.getAltitude(), pos.getBearing(), pos.getLatitude(), pos.getLongitude(), pos.getSpeed()
        });

    }

    @Override
    @Deprecated
    public void remove(PositionModel object) {


    }

    @Override
    @Deprecated
    public void remove(Long id) {


    }

    public void remove(Long id, User user) {
        getSession().createSQLQuery("delete  from `" + makeTableName(user) + "` where id=" + id).addEntity(PositionModel.class).executeUpdate();

    }

    public List<PositionModel> getByPeriod(User user, long begin, long end) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        List<PositionModel> res = new LinkedList<>();
        String sql = "SELECT * FROM " + makeTableName(user) + " WHERE saveTime BETWEEN " + begin + " AND " + end;

        List<Map<String, Object>> rows = template.queryForList(sql);
        PositionModel tmp = null;
        for (Map row : rows) {
            tmp = new PositionModel();
            tmp.setId(Long.parseLong(String.valueOf(row.get("id"))));
            tmp.setSaveTime(Long.parseLong(String.valueOf(row.get("saveTime"))));
            tmp.setAccuracy(Float.parseFloat(String.valueOf(row.get("accuracy"))));
            tmp.setAccuracy(Float.parseFloat(String.valueOf(row.get("accuracy"))));
            tmp.setAltitude(Double.parseDouble(String.valueOf(row.get("altitude"))));
            tmp.setBearing(Float.parseFloat(String.valueOf(row.get("bearing"))));
            tmp.setAccuracy(Float.parseFloat(String.valueOf(row.get("latitude"))));
            tmp.setAccuracy(Float.parseFloat(String.valueOf(row.get("longitude"))));
            tmp.setAccuracy(Float.parseFloat(String.valueOf(row.get("speed"))));
            res.add(tmp);
        }

        return res;
    }
}
