package controller;

import util.PathUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "register", urlPatterns = {PathUtil.URL_REGISTER})
public class RegisterController extends HttpServlet {
}
