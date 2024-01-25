package controller;

import com.google.gson.Gson;
import payload.ResponseData;
import util.Constant;
import util.GsonUtil;
import util.PathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "session", urlPatterns = {PathUtil.URL_SESSION})
public class SessionController extends HttpServlet {
    private Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Enumeration<String> parameterNameList = req.getParameterNames();
        Map<String, Object> responseData = new HashMap<>();
        if (session != null) {
            while (parameterNameList.hasMoreElements()) {
                String parameterName = parameterNameList.nextElement();
                String parameterValue = req.getParameter(parameterName);
                Object obj = session.getAttribute(parameterValue);
                responseData.put(parameterValue, obj);
            }
        }
        String json = gson.toJson(responseData);
        resp.setContentType("application/json");
        resp.setCharacterEncoding(Constant.CHARSET_UTF8);
        try (PrintWriter printWriter = resp.getWriter()) {
            printWriter.print(json);
            printWriter.flush();
        }
    }
}
