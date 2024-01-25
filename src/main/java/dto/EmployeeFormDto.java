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
public class EmployeeFormDto {
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

    public EmployeeFormDto(Employee employee) {
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
    }
}
