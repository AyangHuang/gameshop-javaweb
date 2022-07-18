package dao.impl;

import dao.UserStoreDao;
import entity.UserStore;

import java.sql.SQLException;
import java.util.List;

public class UserStoreDaoImpl extends BaseDao implements UserStoreDao {

    public boolean insetGame(Long user_id, Long game_id) throws SQLException {
        String sql = "insert into t_user_store values(null, ?, ?)";
        if (update(true, sql, user_id, game_id) == 1 ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean queryUserIdAndGameId(Long user_id, Long game_id) throws SQLException {
        String sql = "select order_id from t_user_store where user_id = ? and game_id = ?";
        return queryObject(sql, user_id, game_id) != null;
    }

    @Override
    public List<UserStore> queryAllUserGames(Long user_id) throws SQLException {
        String sql = "select * from t_user_store where user_id = ?";
        return queryMulti(sql, UserStore.class, user_id);
    }
}
