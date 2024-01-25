package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import dto.AccountDto;
import dto.DepartmentDto;
import dto.EmployeeDto;
import entity.Department;
import payload.ResponseData;
import payload.ResponseListData;
import service.DepartmentService;
import service.impl.DepartmentServiceImpl;
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
import java.util.List;

@WebServlet(name = "department", urlPatterns = {PathUtil.URL_DEPARTMENT_ADD, PathUtil.URL_DEPARTMENT_LIST,
        PathUtil.URL_DEPARTMENT_UPDATE, PathUtil.URL_DEPARTMENT_DELETE})
public class DepartmentController extends HttpServlet {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private DepartmentService departmentService = new DepartmentServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        switch (servletPath) {
            case PathUtil.URL_DEPARTMENT_ADD:
                break;
            case PathUtil.URL_DEPARTMENT_LIST:
                getAllDepartment(req, resp);
                break;
            case PathUtil.URL_DEPARTMENT_UPDATE:
                break;
            case PathUtil.URL_DEPARTMENT_DELETE:
                break;
            default:
                break;
        }
    }

    private void getAllDepartment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DepartmentDto> departmentDtoList = departmentService.getAll();
        JsonElement jsonElement = gson.toJsonTree(departmentDtoList, new TypeToken<List<Object>>() {}.getType());
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        boolean isSuccess = departmentDtoList != null;
        String message = "";

        ResponseData responseData = ResponseData.builder()
                .isSuccess(isSuccess)
                .data(jsonArray)
                .message(message)
                .build();
        GsonUtil.responseJson(resp, responseData);
    }
}
