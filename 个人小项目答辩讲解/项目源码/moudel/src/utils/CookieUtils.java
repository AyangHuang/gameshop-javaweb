package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    /**
     * 查找指定名称的Cookie对象
     * @param name
     * @param cookies
     * @return
     */
    public static Cookie findCookie(String name , Cookie[] cookies){
        if (name == null || cookies == null || cookies.length == 0) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }

    public  static void setCookie(HttpServletRequest request, HttpServletResponse response,
                                  String username, String password) {
        Cookie cookie1 = new Cookie("username", username);
        Cookie cookie2 = new Cookie("password", password);
        //设置cookie实现免登录7天，也就是超时时长为7天（因为我每次登录都会重新写入cookie覆盖）
        cookie1.setMaxAge(60 * 60 * 24 * 7);
        cookie2.setMaxAge(60 * 60 * 24 * 7);
        //设置cookie的有效发送路径，全部页面
        cookie1.setPath(request.getContextPath() + "");
        cookie2.setPath(request.getContextPath() + "");
        response.addCookie(cookie1);
        response.addCookie(cookie2);
    }

    public  static Cookie setPageNoCookie(HttpServletRequest request, HttpServletResponse response,
                                  int pageNo) {
        Cookie cookie1 = new Cookie("pageNo", String.valueOf(pageNo));
        cookie1.setMaxAge(60 * 60 * 24 * 30);
        cookie1.setPath(request.getContextPath());
        response.addCookie(cookie1);
        return  cookie1;
    }
}
