package entity;

public class UserStore {
    private Long order_id;
    private Long user_id;
    private Long game_id;

    public UserStore () {}

    public UserStore(Long order_id, Long user_id, Long game_id) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.game_id = game_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
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


