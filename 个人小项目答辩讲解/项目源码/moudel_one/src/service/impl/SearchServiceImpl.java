package service.impl;

import dao.GameDao;
import dao.impl.GameDaoImpl;
import entity.Game;
import service.SearchService;

import java.sql.SQLException;
import java.util.List;

public class SearchServiceImpl implements SearchService {
    GameDao gameDao = new GameDaoImpl();

    @Override
    public List<String> search(String text) throws SQLException {
        text = text.trim();
        return gameDao.queryGameNameByText(text);
    }


    @Override
    public Game searchExact(String game_name) throws SQLException {
        return gameDao.queryGameByGameName(game_name);
    }
}
