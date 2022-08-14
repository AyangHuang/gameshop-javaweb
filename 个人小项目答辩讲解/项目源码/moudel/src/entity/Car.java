package entity;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class Car {
    private int totalCount = 0;
    private int totalPrice = 0;
    //游戏id和游戏Game对象
    private HashMap<Long, Game> items = new HashMap<>();


    //当用户需要用到session时再在用户个人session中创建购物车，省内存
    public static Car getCarBySession(HttpSession session) {
        Object carObj = session.getAttribute("car");
        if (carObj == null) {
            carObj = new Car();
            session.setAttribute("car", carObj);
        }
        return (Car) carObj;
    }


    public boolean isExistGame(Long game_id) {
        return items.containsKey(game_id);
    }

    public void addItem(Long game_id, Game game) {
        items.put(game_id, game);
        totalCount++;
        totalPrice += game.getGame_price();
    }

    public void delete(Long game_id) {
        Game game = items.get(game_id);
        items.remove(game_id);
        totalPrice -= game.getGame_price();
        totalCount--;
    }

    public void clear() {
        items.clear();
        totalCount = 0;
        totalPrice = 0;
    }


    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public HashMap<Long, Game> getItems() {
        return items;
    }

}
