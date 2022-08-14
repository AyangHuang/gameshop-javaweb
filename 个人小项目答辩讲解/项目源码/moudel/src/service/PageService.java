package service;

import entity.Page;

import java.sql.SQLException;

public interface PageService {
    public Page getPage(int pageNo) throws SQLException;



    public String pageJson(int pageNo) throws SQLException;
}
