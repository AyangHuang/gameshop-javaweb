package entity;

public class Salt {
    private Long salt_id;
    private String username;
    private String salt;

    public Salt() {}

    public Salt(Long salt_id, String username, String salt) {
        this.salt_id = salt_id;
        this.username = username;
        this.salt = salt;
    }

    public Long getSalt_id() {
        return salt_id;
    }

    public void setSalt_id(Long salt_id) {
        this.salt_id = salt_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
