package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface UserService {
       /**
        * 判断登录
        * @param username 用户名
        * @param password 密码
        * @return “1”：登录成功， “-1”：没有此用户， “0”：密码错误
        */
       String login(String username, String password) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException;

       /**
        * 判断是否存储该用户名
        * @param name
        * @return ture 代表存在在该用户名
        * @throws SQLException
        */
       Boolean judgeUsername(String name) throws SQLException;

       /**
        * 注册用户
        * @param username
        * @param password
        * @return 注册成功返回true， 失败返回false
        * @throws UnsupportedEncodingException
        * @throws NoSuchAlgorithmException
        * @throws SQLException
        */
       Boolean signUp(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, SQLException;


       /**
        * 通过用户名获取用户id
        */
       Long getUserId(String username) throws SQLException;

       /**\
        * 登录的时候在服务器这边初始话session，cookie，和ServletContext
        * @param request
        * @param user_name
        * @return
        */
       boolean initLogin(HttpServletRequest request, HttpServletResponse response, String user_name, String password) throws SQLException;

       /**
        * 判断是否已经登录
        * @return true 已经登录，false 没有登录
        */
       boolean judgeRepeatLogin(HttpServletRequest request, Long user_id) throws SQLException;

       /**
        * 如果已经登录则，强制下线，删除session
        * @param request
        */
       void forceSignOut(HttpServletRequest request, String username) throws SQLException;


       /**
        * 判断现在网站有多少人
        */
       int numberOnLine(HttpServletRequest request);
}
