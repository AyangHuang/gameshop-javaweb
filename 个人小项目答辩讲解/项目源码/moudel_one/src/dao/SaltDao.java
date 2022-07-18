package dao;

import java.sql.SQLException;

public interface SaltDao {
    /**
     * 根据用户名查 salt
     * @param username 用户名
     * @return salt 盐
     */
    public String querySaltByUsername(String username) throws SQLException;
}
