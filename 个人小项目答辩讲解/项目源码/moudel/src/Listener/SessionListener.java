package Listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        ServletContext servletContext = session.getServletContext();
        Long user_id = (Long) session.getAttribute("user_id");
        if (user_id != null) {
            Map<Long, HttpSession> map = (Map<Long, HttpSession>)servletContext.getAttribute("user_form");
            if (map != null) {
                map.remove(user_id);
                System.out.println("id为---" + user_id + "---的用户退出浏览器");
            }
        }
    }
}
