package service.impl;

import dao.SaltDao;
import dao.UserDao;
import dao.impl.SaltDaoImpl;
import dao.impl.UserDaoImpl;
import service.UserService;
import utils.CookieUtils;
import utils.MD5Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();
    private final SaltDao saltDao = new SaltDaoImpl();


    @Override
    public String login(String username, String password) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String passwordDB = userDao.isExistUsername(username);
        if (!passwordDB.equals("false")) {
            String salt = saltDao.querySaltByUsername(username);
            String passwordMD5Salt = MD5Util.getMD5BySalt(password, salt);
            if (passwordDB.equals(passwordMD5Salt)) {
                return "1";
            }
            return "-1";
        } else {
            return "0";
        }
    }

    @Override
    public Boolean judgeUsername(String name) throws SQLException {
        if (userDao.isExistUsername(name).equals("false")) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean signUp(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException {
        String salt = MD5Util.randomString(32);
        String passwordMD5AndSalt = MD5Util.getMD5BySalt(password, salt);
        if (userDao.insertUserAndSalt(username, passwordMD5AndSalt, salt)) {
            return true;
        }
        return false;
    }


    @Override
    public Long getUserId(String username) throws SQLException {
        return userDao.queryUserIdByUsername(username);
    }

    @Override
    public boolean initLogin(HttpServletRequest request, HttpServletResponse response,  String username, String password) throws SQLException {
        //主动创建session，session的路径默认为工程下的全部路径
        HttpSession session = request.getSession(true);
        Long user_id = this.getUserId(username);
        session.setAttribute("user_id", user_id);
        //设置cookie
        CookieUtils.setCookie(request, response, username, password);
        //设置全局ServletContext,防止同时登录,这里的value存session，重复登录可以删除上一个session
        Map<Long, HttpSession> map= (Map<Long, HttpSession>)request.getServletContext().getAttribute("user_form");
        map.put(user_id, session);
        return true;
    }


    @Override
    public boolean judgeRepeatLogin(HttpServletRequest request, Long user_id) throws SQLException {
        Map<Long, HttpSession> map = (Map<Long, HttpSession>)request.getServletContext().getAttribute("user_form");
        HttpSession session = map.get(user_id);
        if (session != null) {
            return true;
        }
        return false;
    }

    @Override
    public void forceSignOut(HttpServletRequest request, String username) throws SQLException {
        Long user_id = this.getUserId(username);
        if (judgeRepeatLogin(request, user_id)) {
            Map<Long, HttpSession> map = (Map<Long, HttpSession>)request.getServletContext().getAttribute("user_form");
            HttpSession session = map.get(user_id);
            session.invalidate();
        }
    }

    @Override
    public int numberOnLine(HttpServletRequest request) {
        Map<Long, HttpSession> map = (Map)request.getServletContext().getAttribute("user_form");
        return map.size();
    }
}
