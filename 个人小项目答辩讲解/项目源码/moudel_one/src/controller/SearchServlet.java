package controller;

import com.google.gson.Gson;
import entity.Game;
import service.SearchService;
import service.impl.SearchServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class SearchServlet extends BaseServlet{
    private  final SearchService searchService = new SearchServiceImpl();


    protected void ajaxSearch(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String text = request.getParameter("text");
        List<String> list = searchService.search(text);
        Gson gson = new Gson();
        String json = gson.toJson(list);
        response.getWriter().write("{\"games\":" + json + "}");
    }


    protected void ajaxSelect(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String text = request.getParameter("text");
        Game game = searchService.searchExact(text);
        if (game == null) {
            response.getWriter().write("{\"isSuccess\":\"false\"}");
        } else {
            Gson gson = new Gson();
            String gameJson = gson.toJson(game);
            String json = "{\"isSuccess\":\"true\"," + "\"game\":"+ gameJson + "}";
            response.getWriter().write(json);
        }
    }

}
