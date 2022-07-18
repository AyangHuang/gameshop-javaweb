package service.impl;

import com.google.gson.Gson;
import dao.GameDao;
import dao.impl.GameDaoImpl;
import entity.Game;
import entity.Page;
import service.PageService;

import java.sql.SQLException;
import java.util.List;

public class PageServiceImpl implements PageService {
    private final GameDao gameDao = new GameDaoImpl();

    @Override
    public Page getPage(int pageNo) throws SQLException {
        Page page = new Page();
        int itemTotal = gameDao.queryTotalGame();
        int pageTotal = (int) Math.ceil(itemTotal* 1.0 / page.getPageSize());
        int begin = page.getPageSize() * (pageNo - 1);
        List<Game> games = gameDao.queryGamesLimit(begin, page.getPageSize());
        page.setPage(pageNo, pageTotal,itemTotal, games);
        return page;
    }


    public String pageJson(int pageNo) throws SQLException {
        Page page = getPage(pageNo);
        Gson gson = new Gson();
        return  gson.toJson(page);
    }
}
