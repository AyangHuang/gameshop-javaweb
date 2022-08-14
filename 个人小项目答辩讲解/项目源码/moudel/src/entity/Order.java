package entity;

public class Order {
    private Long order_id;
    private String order_no;
    private Long user_id;
    private Long game_id;

    public Order() {}

    public Order(Long order_id, String order_no, Long user_id, Long game_id) {
        this.order_id = order_id;
        this.order_no = order_no;
        this.user_id = user_id;
        this.game_id = game_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getGame_id() {
        return game_id;
    }

    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }
}
