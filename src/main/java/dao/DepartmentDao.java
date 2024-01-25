package dao;

import entity.Account;
import entity.Department;

public interface DepartmentDao extends EntityDao<Department, Integer> {
    @Override
    default Class<Department> getEntityClass() {
        return Department.class;
    }

    Department getByName(String userName);
}
