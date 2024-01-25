package service.impl;

import dao.DepartmentDao;
import dao.impl.DepartmentDaoImpl;
import dto.DepartmentDto;
import entity.Department;
import service.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentDao departmentDao = new DepartmentDaoImpl();

    @Override
    public DepartmentDto create(DepartmentDto departmentDto) {
        Department department = dtoToEntity(departmentDto);
        return new DepartmentDto(departmentDao.create(department));
    }

    @Override
    public DepartmentDto getById(Integer id) {
        return new DepartmentDto(departmentDao.getById(id));
    }

    @Override
    public List<DepartmentDto> getAll() {
        return departmentDao.getAll().stream().map(DepartmentDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean update(DepartmentDto departmentDto) {
        Department department = dtoToEntity(departmentDto);
        return departmentDao.update(department);
    }

    @Override
    public boolean deleteById(Integer id) {
        return departmentDao.deleteById(id);
    }

    private Department dtoToEntity(DepartmentDto dto) {
        return Department.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
