package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null && session.getAttribute("user_id") != null) {
            filterChain.doFilter(httpServletRequest, servletResponse);
        } else {
            servletResponse.getWriter().write("{\"freshSignUp\":\"true\"}");
        }
    }

    @Override
    public void destroy() {}
}
