package grigoryev.controllers.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Checks whether user is authenticated or not.
 * If not forwards user to the sign up page.
 *
 * @author vgrigoryev
 * @version 1
 * @since 16.11.2017
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getRequestURI().contains("/signin")) {
            if (req.getSession().getAttribute("login") != null) {
                req.getSession().invalidate();
            }
            chain.doFilter(request, response);
        } else {
            if (req.getSession().getAttribute("login") == null) {
                ((HttpServletResponse) response).sendRedirect(String.format("%s/signin",
                        req.getContextPath()));
                return;
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
