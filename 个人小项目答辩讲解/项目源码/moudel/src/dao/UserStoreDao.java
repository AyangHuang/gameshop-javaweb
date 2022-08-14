package dao;

import entity.UserStore;

import java.sql.SQLException;
import java.util.List;

public interface UserStoreDao {

    /**
     * 开启线程事务的哦
     * 把游戏加入用户库，
     * @param user_id
     * @param game_id
     * @return 插入失败抛出异常
     * @throws SQLException
     */
    public boolean insetGame(Long user_id, Long game_id) throws SQLException;

    /**
     * 找该用户是否存在游戏
     * @param user_id
     * @param game_id
     * @return true 存在（已购买），未存在（没购买）
     */
    public boolean queryUserIdAndGameId(Long user_id, Long game_id) throws SQLException;

    /**
     * 查找用户所拥有的UserStore，然后再根据里面提供的game_id 即可查出所有的游戏
     * @param user_id
     * @return
     */
    public List<UserStore> queryAllUserGames(Long user_id) throws SQLException;
}
