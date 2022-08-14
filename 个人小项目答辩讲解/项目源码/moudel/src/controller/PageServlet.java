package controller;

import service.PageService;
import service.UserService;
import service.impl.PageServiceImpl;
import service.impl.UserServiceImpl;
import utils.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class PageServlet extends BaseServlet{
    PageService pageService = new PageServiceImpl();
    UserService userService = new UserServiceImpl();

    protected void ajaxPageGame(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int person = userService.numberOnLine(request);
        if (pageNo > 0) {
            CookieUtils.setPageNoCookie(request, response, pageNo);
            String pageJson = pageService.pageJson(pageNo);
            response.getWriter().write("{\"isSuccess\":\"true\"," + "\"person\":" + person +  ",\"page\":" + pageJson + "}");
        } else {
            response.getWriter().write("{\"isSuccess\":\"false\"," + "\"person\":" + person + "}");
        }
    }

    protected void ajaxFirstPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Cookie[] cookies = request.getCookies();
        Cookie pageNoCookie = CookieUtils.findCookie("pageNo", cookies);
        int person = userService.numberOnLine(request);
        if (pageNoCookie == null) {
            pageNoCookie = CookieUtils.setPageNoCookie(request, response, 1);
        }
        int pageNo = Integer.parseInt(pageNoCookie.getValue());
        if (pageNo > 0) {
            String pageJson = pageService.pageJson(pageNo);
            response.getWriter().write("{\"isSuccess\":\"true\"," + "\"person\":" + person +  ",\"page\":" + pageJson + "}");
        } else {
            response.getWriter().write("{\"isSuccess\":\"false\"," + "\"person\":" + person + "}");
        }
    }
}
