package filter;

import dto.AccountDto;
import util.Constant;
import util.PathUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {
    private String[] ignoreUrlList;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignoreUrlList = new String[] {
                "/asset/",
                PathUtil.URL_LOGIN,
                PathUtil.URL_REGISTER,
                PathUtil.URL_SESSION
        };
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        if (isIgnoreFilter(path)) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = req.getSession(false);
            Object accountSession = session == null ? null : session.getAttribute(Constant.SESSION_ACCOUNT);
            if (accountSession instanceof AccountDto) {
                chain.doFilter(request, response);
            } else {
                HttpServletResponse resp = (HttpServletResponse) response;
                resp.sendRedirect(req.getContextPath() + PathUtil.URL_LOGIN);
            }
        }
    }

    private boolean isIgnoreFilter(String request) {
        for (String url : ignoreUrlList) {
            if (request.startsWith(url)) {
                return true;
            }
        }
        return false;
    }
}
