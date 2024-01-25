package dao;

import entity.Employee;

import java.util.List;

public interface EmployeeDao extends EntityDao<Employee, Integer> {
    @Override
    default Class<Employee> getEntityClass() {
        return Employee.class;
    }
    List<Employee> getByAccountIdAndKeyword(int accountId, String keyword, String filterBy, int pageNumber, int pageSize);
    long countResultByAccountIdAndKeyword(int accountId, String keyword, String filterBy, int pageNumber, int pageSize);
}
