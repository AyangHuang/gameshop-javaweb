package entity;

import java.util.List;

public class Page {
    private int pageNo;//当前页码，前端获得
    private int pageTotal;//总页码 Math.ceil 向上取整
    private int itemTotal;//仓库游戏总数 count(*)获取
    private final int pageSize = 10;//每页显示数量
    private final int pageNum = 5;//横条可显示的页码的个数，必须是奇数，正中间是当前页数，两边的页数是pageNum\2
    private List<Game> games = null;//此页面游戏的集合

    public Page() {}

    public void setPage(int pageNo, int pageTotal, int itemTotal, List<Game> games) {
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.itemTotal = itemTotal;
        this.games = games;
    }

    public Page(int pageNo, int pageTotal, int itemTotal, List<Game> games) {
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.itemTotal = itemTotal;
        this.games = games;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(int itemTotal) {
        this.itemTotal = itemTotal;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public List<Game> getGames() {
        return games;
    }

}
