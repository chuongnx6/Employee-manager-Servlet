package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import dto.AccountDto;
import dto.EmployeeDto;
import dto.EmployeeFormDto;
import payload.ResponseData;
import payload.ResponseListData;
import service.EmployeeService;
import service.impl.EmployeeServiceImpl;
import util.Constant;
import util.GsonUtil;
import util.PathUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "employee", urlPatterns = {PathUtil.URL_EMPLOYEE_ADD, PathUtil.URL_EMPLOYEE_LIST, PathUtil.URL_EMPLOYEE_DETAIL,
        PathUtil.URL_EMPLOYEE_UPDATE, PathUtil.URL_EMPLOYEE_DELETE})
public class EmployeeController extends HttpServlet {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private EmployeeService employeeService = new EmployeeServiceImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        switch (servletPath) {
            case PathUtil.URL_EMPLOYEE_ADD:
                addEmployee(req, resp);
                break;
            case PathUtil.URL_EMPLOYEE_DETAIL:
                getEmployee(req, resp);
                break;
            case PathUtil.URL_EMPLOYEE_LIST:
                getAllEmployee(req, resp);
                break;
            case PathUtil.URL_EMPLOYEE_UPDATE:
                updateEmployee(req, resp);
                break;
            case PathUtil.URL_EMPLOYEE_DELETE:
                deleteEmployee(req, resp);
                break;
            default:
                break;
        }
    }

    private void addEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeFormDto employeeFormDto = getEmployeeDto(req, resp);

        boolean isSuccess = employeeService.create(employeeFormDto) != null;
        String message = "";
        ResponseData responseData = ResponseData.builder()
                .isSuccess(isSuccess)
                .message(message)
                .build();
        GsonUtil.responseJson(resp, responseData);
    }

    private void getAllEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        AccountDto accountDto = (AccountDto) session.getAttribute(Constant.SESSION_ACCOUNT);
        JsonArray jsonArray = new JsonArray();
        boolean isSuccess = false;
        String message;
        String keyword = req.getParameter("keyword");
        String filterBy = req.getParameter("filterBy");
        String pageNumberStr = req.getParameter("pageNumber");
        String pageSizeStr = req.getParameter("pageSize");
        long firstRow = 0;
        int pageNumber = 0;
        int pageSize = 0;
        long totalResult = 0;
        int totalPage = 0;
        try {
            pageNumber = Integer.parseInt(pageNumberStr);
            pageSize = Integer.parseInt(pageSizeStr);
            firstRow = (long) (pageNumber - 1) * pageSize + 1;
            totalResult = employeeService.countResultByAccountIdAndKeyword(accountDto.getId(), keyword, filterBy, pageNumber, pageSize);
            totalPage = (int) Math.ceil((double) totalResult / pageSize);
            List<EmployeeDto> employeeDtoList = employeeService.getByAccountIdAndKeyword(accountDto.getId(), keyword, filterBy, pageNumber, pageSize);
            JsonElement jsonElement = gson.toJsonTree(employeeDtoList, new TypeToken<List<Object>>() {}.getType());
            jsonArray = jsonElement.getAsJsonArray();
            isSuccess = true;
            message = "";
        } catch (NumberFormatException nfe) {
            message = "Invalid parameter.";
        }

        ResponseListData responseListData = ResponseListData.builder()
                .isSuccess(isSuccess)
                .firstRow(firstRow)
                .currentPage(pageNumber > 0 ? pageNumber : null)
                .pageSize(pageSize > 0 ? pageSize : null)
                .totalResult(totalResult)
                .totalPage(totalPage)
                .data(jsonArray)
                .message(message)
                .build();
        GsonUtil.responseJson(resp, responseListData);
    }

    private void getEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeIdStr = req.getParameter("employeeId");
        boolean isSuccess = false;
        String message;
        EmployeeFormDto employeeFormDto = null;
        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            employeeFormDto =  employeeService.getById(employeeId);
            message = "";
        } catch (NumberFormatException nfe) {
            message = "Invalid parameter.";
        }
        ResponseData responseData = ResponseData.builder()
                .isSuccess(isSuccess)
                .data(employeeFormDto)
                .message(message)
                .build();
        GsonUtil.responseJson(resp, responseData);
    }

    private void updateEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmployeeFormDto employeeFormDto = getEmployeeDto(req, resp);

        boolean isSuccess = employeeService.update(employeeFormDto);
        String message = "";
        ResponseData responseData = ResponseData.builder()
                .isSuccess(isSuccess)
                .message(message)
                .build();
        GsonUtil.responseJson(resp, responseData);
    }

    private void deleteEmployee(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeIdStr = req.getParameter("employeeId");
        boolean isSuccess = false;
        String message;
        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            isSuccess = employeeService.deleteById(employeeId);
            message = "";
        } catch (NumberFormatException nfe) {
            message = "Invalid parameter.";
        }
        ResponseData responseData = ResponseData.builder()
                .isSuccess(isSuccess)
                .message(message)
                .build();
        GsonUtil.responseJson(resp, responseData);
    }

    private EmployeeFormDto getEmployeeDto(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuffer.append(line);
        }
        String jsonData = jsonBuffer.toString();
        EmployeeFormDto employeeFormDto = gson.fromJson(jsonData, EmployeeFormDto.class);
        HttpSession session = req.getSession(false);
        AccountDto accountDto = (AccountDto) session.getAttribute(Constant.SESSION_ACCOUNT);
        employeeFormDto.setAccountId(accountDto.getId());
        return employeeFormDto;
    }
}
