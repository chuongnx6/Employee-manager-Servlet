package controller;

import dto.AccountDto;
import service.LoginService;
import service.impl.LoginServiceImpl;
import util.Constant;
import util.FileUtil;
import util.PathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = {PathUtil.URL_LOGIN, PathUtil.URL_LOGOUT})
public class LoginController extends HttpServlet {
    private LoginService loginService;

    @Override
    public void init() throws ServletException {
        loginService = new LoginServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        switch (path) {
            case PathUtil.URL_LOGIN:
                login(req, resp);
                break;
            case PathUtil.URL_LOGOUT:
                logout(req, resp);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        AccountDto accountDto = loginService.checkLogin(userName, password);
        HttpSession session = req.getSession(true);
        if (accountDto != null) {
            session.setAttribute(Constant.SESSION_ACCOUNT, accountDto);
            session.removeAttribute("userNameFail");
            session.removeAttribute("passwordFail");
            session.removeAttribute("loginMessage");
            resp.sendRedirect(req.getContextPath() + PathUtil.URL_HOME);
        } else {
            session.setAttribute("userNameFail", userName);
            session.setAttribute("passwordFail", password);
            session.setAttribute("loginMessage", "The username or password that you've entered is incorrect.");
            req.getRequestDispatcher(FileUtil.FILE_LOGIN).forward(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(FileUtil.FILE_LOGIN).forward(req, resp);
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute(Constant.SESSION_ACCOUNT);
        }
        resp.sendRedirect(req.getContextPath() + PathUtil.URL_LOGIN);
    }
}
