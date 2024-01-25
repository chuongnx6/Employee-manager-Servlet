package dao;

import entity.Employee;

public interface EmployeeDao extends EntityDao<Employee, Integer> {
    @Override
    default Class<Employee> getEntityClass() {
        return Employee.class;
    }
}
