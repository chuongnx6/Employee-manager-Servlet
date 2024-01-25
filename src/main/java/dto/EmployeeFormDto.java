package dto;

import com.google.gson.annotations.JsonAdapter;
import dao.impl.AccountDaoImpl;
import dao.impl.DepartmentDaoImpl;
import entity.Account;
import entity.Department;
import entity.Employee;
import entity.enumeration.Gender;
import lombok.*;
import util.GsonLocalDateAdapter;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFullDto {
    private int id;
    private String firstName;
    private String lastName;
    @JsonAdapter(GsonLocalDateAdapter.class)
    private LocalDate dateOfBirth;
    private String phone;
    private String gender;
    private String address;
    private String remark;
    private int departmentId;
    private int accountId;
//    @Setter(AccessLevel.NONE)
//    private String departmentName;

    public EmployeeFullDto(Employee employee) {
        id = employee.getId();
        firstName = employee.getFirstName();
        lastName = employee.getLastName();
        dateOfBirth = employee.getDateOfBirth();
        phone = employee.getPhone();
        gender = employee.getGender().toString();
        address = employee.getAddress();
        remark = employee.getRemark();
        departmentId = employee.getDepartment().getId();
        accountId = employee.getAccount().getId();
//        departmentName = employee.getDepartment().getName();
    }

    public Employee loadFromDto(EmployeeFullDto dto) {
        Department department = new DepartmentDaoImpl().getById(dto.getDepartmentId());
        Account account = new AccountDaoImpl().getById(dto.getAccountId());
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

//    public String getDepartmentName() {
//        return new DepartmentDaoImpl().getById(departmentId).getName();
//    }
}
