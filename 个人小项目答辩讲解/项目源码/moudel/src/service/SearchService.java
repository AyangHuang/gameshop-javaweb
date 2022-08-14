package service;

import entity.Game;

import java.sql.SQLException;
import java.util.List;

public interface SearchService {
    /**
     * 模糊搜索游戏
     * @return
     */
    public List<String> search(String text) throws SQLException;


    /**
     * 精确搜索游戏
     * @param game_name
     * @return
     */
    public Game searchExact(String game_name) throws SQLException;
}
