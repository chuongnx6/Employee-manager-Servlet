package service;

import dto.EmployeeDto;
import dto.EmployeeFormDto;

import java.util.List;

public interface EmployeeService{
    EmployeeFormDto create(EmployeeFormDto employeeDto);
    EmployeeFormDto getById(Integer id);
    List<EmployeeDto> getAll();
    boolean update(EmployeeFormDto employeeDto);
    boolean deleteById(Integer id);

    List<EmployeeDto> getByAccountIdAndKeyword(int accountId, String keyword, String filterBy, int pageNumber, int pageSize);
    long countResultByAccountIdAndKeyword(int accountId, String keyword, String filterBy, int pageNumber, int pageSize);
}
