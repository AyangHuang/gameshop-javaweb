package dao.impl;

import dao.GameDao;
import entity.Game;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDaoImpl extends BaseDao<Game> implements GameDao {
    @Override
    public List<Game> queryGamesLimit(int begin, int pageSize) throws SQLException {
        String sql = "select * from t_game limit ?, ?";
        return queryMulti(sql, Game.class, begin, pageSize);
    }

    @Override
    public Game queryGameByGameId(Long game_id) throws SQLException {
        String sql = "select * from t_game where game_id = ?";
        return querySingle(sql, Game.class, game_id);
    }

    @Override
    public Game queryGameByGameName(String game_name) throws SQLException {
        String sql = "select * from t_game where game_name = ?";
        return querySingle(sql, Game.class, game_name);
    }

    @Override
    public String queryGameNameByGameId(Long game_id) throws SQLException {
        String sql = "select game_name from t_game where game_id = ?";
        return (String) queryObject(sql, game_id);
    }


    public  List<String> queryGameNameByText(String text) throws SQLException {
        text = "%" + text + "%";
        String sql = "select game_name from t_game where game_name like ? limit 0, 8";
        List<Game> games = queryMulti(sql, Game.class, text);
        List<String> gameNames = new ArrayList<>();
        for (Game game : games) {
            gameNames.add(game.getGame_name());
        }
        return gameNames;
    }

    @Override
    public int queryTotalGame() throws SQLException {
        String sql = "select count(*) from t_game";
        return ((Long)queryObject(sql)).intValue();
    }
}
