package dao.impl;

import dao.SaltDao;
import entity.Salt;

import java.sql.SQLException;

public class SaltDaoImpl extends BaseDao<Salt> implements SaltDao {

    @Override
    public String querySaltByUsername(String username) throws SQLException {
        String sql = "select salt from t_salt where username=?";
        return (String) queryObject(sql, username);
    }
}
