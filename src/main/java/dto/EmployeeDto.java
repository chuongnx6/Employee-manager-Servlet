package dto;

import com.google.gson.annotations.JsonAdapter;
import entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import util.GsonLocalDateAdapter;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private int id;
    private String firstName;
    private String lastName;
    @JsonAdapter(GsonLocalDateAdapter.class)
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private String departmentName;

    public EmployeeDto(Employee employee) {
        id = employee.getId();
        firstName = employee.getFirstName();
        lastName = employee.getLastName();
        dateOfBirth = employee.getDateOfBirth();
        phone = employee.getPhone();
        address = employee.getAddress();
        departmentName = employee.getDepartment().getName();
    }
}
