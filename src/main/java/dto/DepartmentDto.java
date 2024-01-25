package dto;

import entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    private int id;
    private String name;

    public DepartmentDto(Department department) {
        id = department.getId();
        name = department.getName();
    }
}
