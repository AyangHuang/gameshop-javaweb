package dao;

import entity.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameDao {
    /**
     * 分页查询游戏
     * @param begin
     * @param pageSize
     * @return List<Game>
     * @throws SQLException
     */
    public List<Game> queryGamesLimit(int begin, int pageSize) throws SQLException;

    /**
     * 根据游戏id查游戏Game
     * @param game_id
     * @return 返回游戏对象，找不到，返回null
     */
    public Game queryGameByGameId(Long game_id) throws SQLException;

    /**
     * 根据游戏名字查游戏
     * @param game_name
     * @return null 为查无此游戏
     * @throws SQLException
     */
    public Game queryGameByGameName(String game_name) throws SQLException;

    /**
     * 根据游戏id找游戏名字
     * @param game_id
     * @return 返回string游戏名字,找不到返回null
     */
    public String queryGameNameByGameId(Long game_id) throws SQLException;


    /**
     * 搜索的模糊查询功能(无视大小写),limit 最多8个
     */
    public  List<String> queryGameNameByText(String text) throws SQLException;

    /**
     * 返回游戏仓库总数
     * @return
     */
    public int queryTotalGame() throws SQLException;
}
