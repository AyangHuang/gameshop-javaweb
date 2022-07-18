package controller;

import service.UserService;
import service.impl.UserServiceImpl;
import utils.CookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginServlet extends BaseServlet{
    private final UserService userService = new UserServiceImpl();

    protected void ajaxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NoSuchAlgorithmException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String loginSuccess = userService.login(username, password);
        if (loginSuccess.equals("1")) {//账号密码正确
            System.out.println("用户:" + username + " 密码：" + password +  " 登录成功!");
            userService.forceSignOut(request, username);
            userService.initLogin(request, response, username, password);
            response.getWriter().write("{\"isSuccess\":\"true\"}");
//            //test
//            HttpSession session = request.getSession(true);
//            session.invalidate();
//            if (session != null) {
//                System.out.println("invalidate"+ "不为null");
//            }

        } else {
            System.out.println("用户:" + username + " 密码：" + password +  " 登录失败!密码错误！");
            response.getWriter().write("{\"isSuccess\":\"false\"}");
        }
    }

    protected  void ajaxSignUpJudgeUsername(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String username = request.getParameter("username");
        Boolean aBoolean = userService.judgeUsername(username);
        if (aBoolean) {
            response.getWriter().write("{\"isSuccess\":\"true\"}");
        } else {
            response.getWriter().write("{\"isSuccess\":\"false\"}");
        }
    }


    protected  void ajaxSignUp(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NoSuchAlgorithmException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Boolean isSuccessSignUp = userService.signUp(username, password);
        if (isSuccessSignUp) {
            response.getWriter().write("{\"isSuccess\":\"true\"}");
        } else {
            response.getWriter().write("{\"isSuccess\":\"false\"}");
        }
    }


    protected void ajaxLoginOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            int person = userService.numberOnLine(request);
            response.getWriter().write("{\"isSuccess\":\"true\","+ "\"person\":"  +person +  "}");
        } else {
            response.getWriter().write("{\"isSuccess\":\"false\"}");
        }
    }


    protected void ajaxAutoSignIn(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, NoSuchAlgorithmException {
        //说明session还在登录状态，不用理..(特别注意，这里要false，不然找不到直接自动创建一个新的session）
        int person = userService.numberOnLine(request);
        if (request.getSession(false) != null) {}
        else {
            //没有session，只有cookie且未过期，判断后自动登录
            Cookie[] cookies = request.getCookies();
            String username = CookieUtils.findCookie("username", cookies).getValue();
            String password = CookieUtils.findCookie("password", cookies).getValue();
            if (username == null || password == null) {
                response.getWriter().write("{\"isSuccess\":\"false\","+ "\"person\":" + person + "}");
                return;
            }
            //判断登录用户名和密码是否正确
            String loginSuccess = userService.login(username, password);
            if (loginSuccess.equals("1")) {
                userService.forceSignOut(request, username);
                userService.initLogin(request, response, username, password);
                person = userService.numberOnLine(request);
            } else {
                response.getWriter().write("{\"isSuccess\":\"false\","+ "\"person\":" + person +"}");
                return;
            }
        }
        response.getWriter().write("{\"isSuccess\":\"true\","+ "\"person\":" + person +"}");
    }
}
