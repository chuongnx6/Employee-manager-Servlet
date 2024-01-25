package entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "dbo", name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @EqualsAndHashCode.Include
    @Column(name = "name", nullable = false)
    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "department")
    private Set<Employee> employeeSet;
}
