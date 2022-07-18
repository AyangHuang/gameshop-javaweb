package test;

import com.google.gson.Gson;
import dao.GameDao;
import dao.OrderDao;
import dao.UserDao;
import dao.UserStoreDao;
import dao.impl.GameDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.UserDaoImpl;
import dao.impl.UserStoreDaoImpl;
import entity.Game;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import service.UserService;
import service.impl.UserServiceImpl;
import utils.GenerateNum;
import utils.JDBCUtil;
import utils.MD5Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

    @org.junit.Test
    public void testMD5() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(MD5Util.getMD5("123456").equals("e10adc3949ba59abbe56e057f20f883e"));
    }

    @org.junit.Test
    public void testMD5AndSalt() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String salt = MD5Util.randomString(32);
        System.out.println("盐" + salt);
        String passwordMD5 = MD5Util.getMD5("123456");
        System.out.println("一次MD5" + passwordMD5);
        String passwordAndSalt = MD5Util.getMD5BySalt(passwordMD5, salt);
        System.out.println("二次MD5" + passwordAndSalt);
    }

    @org.junit.Test
    public void testGetPasswordByUsername() throws SQLException {
        String username = "123456";
        UserDao userDao = new UserDaoImpl();
        String password = userDao.queryPasswordByUsername(username);
        System.out.println(password);
    }

    @org.junit.Test
    public void testLogin() throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String username = "123456";
        String password = "e10adc3949ba59abbe56e057f20f883e";
        UserService userService = new UserServiceImpl();
        String login  = userService.login(username, password);
        System.out.println(login);
    }

    @org.junit.Test
    public void testIsisExistUsername() throws SQLException {
        String username = "123456";
        String username2 = "1234567";
        UserDao userDao = new UserDaoImpl();
        String str = userDao.isExistUsername(username);
        String str2 = userDao.isExistUsername(username2);
        System.out.println(str);
        System.out.println(str2);
    }

    @org.junit.Test
    public void testGameDao() throws SQLException {
        Connection connection = JDBCUtil.getConnection(false);
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select * from t_game";
        List<Game> set = queryRunner.query(connection, sql, new BeanListHandler<>(Game.class));
        for (Game game : set) {
            System.out.println(game);
        }
    }

    @org.junit.Test
    public void testOrderNumber() {
        while (true) {
            TestOrderNumber testOrderNumber = new TestOrderNumber();
            Thread t = new Thread(testOrderNumber);
            t.start();
        }
    }

    @org.junit.Test
    public void testDaoGetGameLimit() throws SQLException {
        GameDao gameDao = new GameDaoImpl();
        List<Game> list = gameDao.queryGamesLimit(0, 10);
//        for (Game game : list) {
//            System.out.println(game);
//        }
        Gson gson = new Gson();
        String  strJson = gson.toJson(list);
        System.out.println(strJson);
//        [{
//                    "game_id": 1,
//                    "game_name": "光之傳說",
//                    "release_date": "九月 13, 2021",
//                    "game_price": 48,
//                    "game_publisher": "株式会社デスクワークス",
//                    "game_developer": "株式会社アニプレックス",
//                    "image_url": "https://cdn.cloudflare.steamstatic.com/steam/apps/1839510/header_schinese.jpg?t\u003d1656472478",
//                    "game_introduce": "冒險的時間到了！ 下課鐘聲響起，角色扮演遊戲時間正式上線！快點一同暢玩由超級熱愛遊戲的少年「健太」，在筆記本上親自描繪而出的角色扮演遊戲！",
//                    "game_type": "role_play"
//        }, {
//                    "game_id": 2,
//                    "game_name": "Haven",
//                    "release_date": "十二月 3, 2020",

    }


    @org.junit.Test
    public void testQueryUserByUserId() throws SQLException {
        UserDao userDao = new UserDaoImpl();
        Long along = new Long(1);
        System.out.println(userDao.queryUserIdByUsername("123456"));
        System.out.println(userDao.queryUserByUserId(along));
    }

    @org.junit.Test
    public void testQueryGameByNameIdAndQueryGameNameByGameId() throws SQLException {
        GameDao gameDao = new GameDaoImpl();
        Long along = new Long(0);
        System.out.println(gameDao.queryGameNameByGameId(along));
        System.out.println(gameDao.queryGameByGameId(along));
    }

    @org.junit.Test
    public void testBuy() throws SQLException {
        OrderDao orderDao = new OrderDaoImpl();
        UserStoreDao userStoreDao = new UserStoreDaoImpl();
        try {
            String order  = GenerateNum.getInstance().GenerateOrder();
            Long along = new Long(1);
            orderDao.insertOrder(order, along, along);
//            int a = 0;
//            int b = 2 / a;
            userStoreDao.insetGame(along, along);
            JDBCUtil.commitAndClose();
        } catch (SQLException e) {
            JDBCUtil.rollbackAndClose();
        }
    }

}

/**
 * 测试订单的多线程
 */
class TestOrderNumber implements Runnable {

    public static Set<String>  set = new HashSet<>();
    @Override
    public void run() {
        String number = GenerateNum.getInstance().GenerateOrder();
        if (!set.contains(number)) {
            System.out.println(number.length());
            set.add(number);
        } else {
            System.out.println("存在了");
        }
    }
}