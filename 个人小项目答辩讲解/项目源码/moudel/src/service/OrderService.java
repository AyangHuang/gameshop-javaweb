package service;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public interface OrderService {
    /**
     * 购买，加入订单，加入个人仓库
     * @param user_id
     * @param game_id
     * @return “1” 成功， "0" 已存在改游戏， 异常抛出，加入失败，filter会回滚
     * @throws SQLException
     */
    public String addOrder(Long user_id, Long game_id) throws SQLException;


    /**
     *
     * @return "-1"表示仓库已存在，”-2“表示购物车已存在， ”1“表示成功
     */
    public String addCar(HttpSession session, Long game_id) throws SQLException;




    /**
     * 把一个物品购买入库
     * @param session
     * @param game_id
     * @return
     * @throws SQLException
     */
    public boolean bugOne(HttpSession session, Long game_id) throws SQLException;


    /**
     * 直接取出session的游戏，并且封装为json返回
     * @param session
     * @return
     */
    public String getCar(HttpSession session);


    /**
     * 移除购物车中的某一种游戏
     * @param session
     * @param game_id
     * @return
     */
    public String removeCar(HttpSession session, Long game_id);


    /**
     * 买下购物车所有物品
     * @param session
     * @return
     * @throws SQLException
     */
    public boolean buyAll(HttpSession session) throws SQLException;


    /**
     * 获取用户所有的游戏
     * @param user_id
     * @return
     * @throws SQLException
     */
    public String getAllUserGame(Long user_id) throws SQLException;
}
