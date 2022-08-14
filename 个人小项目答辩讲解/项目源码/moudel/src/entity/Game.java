package entity;

import java.sql.Date;

public class Game {
    private Long game_id;
    private String game_name;
    private Date release_date;
    private Integer game_price;
    private String game_publisher;
    private String game_developer;
    private String image_url;
    private String game_introduce;
    private String game_type;

    public Game() {}

    public Game(Long game_id, String game_name, Date release_date, Integer game_price, String game_publisher, String game_developer, String image_url, String game_introduce, String game_type) {
        this.game_id = game_id;
        this.game_name = game_name;
        this.release_date = release_date;
        this.game_price = game_price;
        this.game_publisher = game_publisher;
        this.game_developer = game_developer;
        this.image_url = image_url;
        this.game_introduce = game_introduce;
        this.game_type = game_type;
    }

    public Long getGame_id() {
        return game_id;
    }

    public void setGame_id(Long game_id) {
        this.game_id = game_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Integer getGame_price() {
        return game_price;
    }

    public void setGame_price(Integer game_price) {
        this.game_price = game_price;
    }

    public String getGame_publisher() {
        return game_publisher;
    }

    public void setGame_publisher(String game_publisher) {
        this.game_publisher = game_publisher;
    }

    public String getGame_developer() {
        return game_developer;
    }

    public void setGame_developer(String game_developer) {
        this.game_developer = game_developer;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getGame_introduce() {
        return game_introduce;
    }

    public void setGame_introduce(String game_introduce) {
        this.game_introduce = game_introduce;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    @Override
    public String toString() {
        return "Game{" +
                "game_id=" + game_id +
                ", game_name='" + game_name + '\'' +
                ", release_date=" + release_date +
                ", game_price=" + game_price +
                ", game_publisher='" + game_publisher + '\'' +
                ", game_developer='" + game_developer + '\'' +
                ", image_url='" + image_url + '\'' +
                ", game_introduce='" + game_introduce + '\'' +
                ", game_type='" + game_type + '\'' +
                '}';
    }
}
