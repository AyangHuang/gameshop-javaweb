package filter;

import utils.JDBCUtil;

import javax.servlet.*;
import java.io.IOException;
import java.sql.SQLException;

public class MainFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            servletRequest.setCharacterEncoding("UTF-8");
            // 解决响应的中文乱码，下面不可取，因为在获取css文件时写入contentype = html会让浏览器无法解析css文件
//            servletResponse.setContentType("text/html; charset=UTF-8");
            servletResponse.setCharacterEncoding("UTF-8");
            filterChain.doFilter(servletRequest, servletResponse);
            //事务提交
            JDBCUtil.commitAndClose();
        } catch (Exception e) {
            servletResponse.getWriter().write("{\"isSuccess\":\"error\"}");
            try {
                //事务回滚
                JDBCUtil.rollbackAndClose();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void destroy() {

    }
}
