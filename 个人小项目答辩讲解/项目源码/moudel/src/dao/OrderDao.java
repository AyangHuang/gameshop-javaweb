package dao;

import java.sql.SQLException;

public interface OrderDao {
    /**
     * 加入订单
     * @param order_id 订单id
     * @param user_id
     * @param game_id
     * @return
     */
    public boolean insertOrder(String order_id, Long user_id, Long game_id) throws SQLException;
}
