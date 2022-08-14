package controller;

import service.OrderService;
import service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class OrderServlet extends BaseServlet{
    private final OrderService orderService = new OrderServiceImpl();
    /**
     * "1"购买成功，”-1“购买失败， ”0” 请勿重复购买
     * @param request
     * @param response
     * @throws IOException
     */
    protected void ajaxBuy(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String game_id_str = request.getParameter("game_id");
        Long game_id = Long.parseLong(game_id_str);
        //session一定存在，不用判断了，因为filter已经过滤过了
        HttpSession session = request.getSession(false);
        boolean tag = orderService.bugOne(session, game_id);
        if (tag) {
            response.getWriter().write("{\"isSuccess\":\"true\"}");
        } else {
            response.getWriter().write("{\"isSuccess\":\"false\"}");
        }
    }


    protected void ajaxAddCar(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String game_id_str = request.getParameter("game_id");
        Long game_id = Long.parseLong(game_id_str);
        String tag = orderService.addCar(request.getSession(false), game_id);
        if (tag.equals("-1")) {
            response.getWriter().write("{\"isSuccess\":\"-1\"}");
        } else if (tag.equals("-2")) {
            response.getWriter().write("{\"isSuccess\":\"-2\"}");
        } else if (tag.equals("1")) {
            response.getWriter().write("{\"isSuccess\":\"1\"}");
        }
    }


    protected void ajaxGetCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write(orderService.getCar(request.getSession()));;
    }

    protected void ajaxRemoveCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long game_id = Long.parseLong(request.getParameter("game_id"));
        HttpSession session = request.getSession(false);
        response.getWriter().write(orderService.removeCar(session, game_id));
    }

    protected void ajaxBuyAll(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        orderService.buyAll(request.getSession());
        response.getWriter().write("{\"isSuccess\":\"true\"}");
    }

    protected void ajaxGetUserStore(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HttpSession session = request.getSession();
        Long user_id = (Long)session.getAttribute("user_id");
        String json  = orderService.getAllUserGame(user_id);
        response.getWriter().write(json);
    }

}
