package service.impl;

import dao.AccountDao;
import dao.DepartmentDao;
import dao.EmployeeDao;
import dao.impl.AccountDaoImpl;
import dao.impl.DepartmentDaoImpl;
import dao.impl.EmployeeDaoImpl;
import dto.EmployeeDto;
import dto.EmployeeFormDto;
import entity.Account;
import entity.Department;
import entity.Employee;
import entity.enumeration.Gender;
import service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {
    private AccountDao accountDao = new AccountDaoImpl();
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    private DepartmentDao departmentDao = new DepartmentDaoImpl();
    @Override
    public EmployeeFormDto create(EmployeeFormDto employeeFormDto) {
        Employee employee = dtoToEntity(employeeFormDto);
        return new EmployeeFormDto(employeeDao.create(employee));
    }

    @Override
    public EmployeeFormDto getById(Integer id) {
        return new EmployeeFormDto(employeeDao.getById(id));
    }

    @Override
    public List<EmployeeDto> getByAccountIdAndKeyword(int accountId, String keyword, String filterBy, int pageNumber, int pageSize) {
        List<Employee> employeeList = employeeDao.getByAccountIdAndKeyword(accountId, keyword, filterBy, pageNumber, pageSize);
        return employeeList.stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    @Override
    public long countResultByAccountIdAndKeyword(int accountId, String keyword, String filterBy, int pageNumber, int pageSize) {
        return employeeDao.countResultByAccountIdAndKeyword(accountId, keyword, filterBy, pageNumber, pageSize);
    }

    @Override
    public List<EmployeeDto> getAll() {
        return employeeDao.getAll().stream().map(EmployeeDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean update(EmployeeFormDto employeeFormDto) {
        Employee employee = dtoToEntity(employeeFormDto);
        return employeeDao.update(employee);
    }

    @Override
    public boolean deleteById(Integer id) {
        return employeeDao.deleteById(id);
    }

    private Employee dtoToEntity(EmployeeDto dto) {
        Department department = departmentDao.getByName(dto.getDepartmentName());
        return Employee.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .department(department)
                .build();
    }

    private Employee dtoToEntity(EmployeeFormDto dto) {
        Department department = departmentDao.getById(dto.getDepartmentId());
        Account account = accountDao.getById(dto.getAccountId());
        return Employee.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .phone(dto.getPhone())
                .gender(Gender.valueOf(dto.getGender().toUpperCase()))
                .address(dto.getAddress())
                .remark(dto.getRemark())
                .department(department)
                .account(account)
                .build();
    }
}
