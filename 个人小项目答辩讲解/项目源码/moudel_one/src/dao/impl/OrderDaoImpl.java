package dao.impl;

import dao.OrderDao;

import java.sql.SQLException;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public boolean insertOrder(String order_id, Long user_id, Long game_id) throws SQLException {
        String sql = "insert into t_order values(null, ?, ?, ?)";
        return update(true, sql, order_id, user_id, game_id) == 1;
    }
}
