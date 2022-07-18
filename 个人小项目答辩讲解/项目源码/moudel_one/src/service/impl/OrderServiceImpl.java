package service.impl;

import com.google.gson.Gson;
import dao.GameDao;
import dao.OrderDao;
import dao.UserStoreDao;
import dao.impl.GameDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.UserStoreDaoImpl;
import entity.Car;
import entity.Game;
import entity.UserStore;
import service.OrderService;
import utils.GenerateNum;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderServiceImpl implements OrderService {
    private final UserStoreDao userStoreDao = new UserStoreDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();
    private final GameDao gameDao = new GameDaoImpl();

    @Override
    public String addOrder(Long user_id, Long game_id) throws SQLException {
        //已存在该商品
        if (userStoreDao.queryUserIdAndGameId(user_id, game_id)) {
            return "0";
        } else {
            userStoreDao.insetGame(user_id, game_id);
            orderDao.insertOrder(GenerateNum.getInstance().GenerateOrder(), user_id,game_id);
            return "1";
        }
    }


    public String addCar(HttpSession session, Long game_id) throws SQLException {
        Long user_id = (Long) session.getAttribute("user_id");
        if (userStoreDao.queryUserIdAndGameId(user_id, game_id)) {
            return "-1";
        }
        Car car =  Car.getCarBySession(session);
        if (car.isExistGame(game_id)) {
            return "-2";
        }
        Game game = gameDao.queryGameByGameId(game_id);
        car.addItem(game_id, game);
        return "1";
    }

    public boolean bugOne(HttpSession session, Long game_id) throws SQLException {
        Long user_id = (Long)session.getAttribute("user_id");
        String tag = this.addOrder(user_id, game_id);
        if (tag.equals("1")) {
            //如果有购物车且购物车中有这件物品，就删掉
            Car car = (Car) session.getAttribute("car");
            if (car != null && car.isExistGame(game_id)) {
                car.delete(game_id);
            }
            return true;
        } else if (tag.equals("0")) {
            return false;
        }
        return false;
    }


    public String getCar(HttpSession session) {
        Car car = (Car) session.getAttribute("car");
        if (car == null) {
            return "{\"totalPrice\":" + 0 +
                    ",\"totalCount\":" + 0 + ",\"games\":" + "[]}";
        }
        String totalPrice = String.valueOf(car.getTotalPrice());
        String totalCount = String.valueOf(car.getTotalCount());
        Gson gson = new Gson();
        String gamesJson = gson.toJson(car.getItems().values());
        String json = "{\"totalPrice\":" + totalPrice +
                ",\"totalCount\":" + totalCount + ",\"games\":" + gamesJson + "}";
        return json;
    }



    public String removeCar(HttpSession session, Long game_id) {
        Car car = (Car) session.getAttribute("car");
        if (car != null) {
            car.delete(game_id);
            return "{\"isSuccess\":\"true\",\"totalPrice\":" + car.getTotalPrice() +
                    ",\"totalCount\":" + car.getTotalCount() + ", \"game_id\":" + game_id + "}";
        }
        return null;
    }


    public boolean buyAll(HttpSession session) throws SQLException {
        Car car = (Car) session.getAttribute("car");
        Long user_id = (Long)session.getAttribute("user_id");
        if (car != null) {
            Set<Long> longs = car.getItems().keySet();
//            int i = 0;
//            int b = 0;
            for (Long game_id : longs) {
//                if (i == 2) {
//                    i = i / b;
//                }
//                i++;
                this.addOrder(user_id, game_id);
            }
            //最后最清空购物车，当然也可以边遍历边删除，但是比较麻烦，直接全部加入再删除
            //这样即使报错回滚了事务也购物车一样不会少
            car.clear();
            return true;
        }
        return false;
    }

    public String getAllUserGame(Long user_id) throws SQLException {
        List<UserStore> userStores = userStoreDao.queryAllUserGames(user_id);
        List<Game> games = new ArrayList<>();
        int totalPrice = 0;
        for (UserStore userStore : userStores) {
            Game game = gameDao.queryGameByGameId(userStore.getGame_id());
            games.add(game);
            totalPrice += game.getGame_price();
        }
        int totalCount = games.size();
        Gson gson = new Gson();
        String gamesJson = gson.toJson(games);
        return "{\"totalPrice\":" + totalPrice + ",\"totalCount\":" + totalCount +
                ",\"games\":" + gamesJson + "}";
    }



}




















