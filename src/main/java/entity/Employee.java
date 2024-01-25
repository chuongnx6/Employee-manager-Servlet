package entity;

import com.google.gson.annotations.JsonAdapter;
import entity.enumeration.Gender;
import lombok.*;
import util.GsonLocalDateAdapter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(schema = "dbo", name = "employee")
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @EqualsAndHashCode.Include
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @EqualsAndHashCode.Include
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @EqualsAndHashCode.Include
    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @EqualsAndHashCode.Include
    @JsonAdapter(GsonLocalDateAdapter.class)
    @Column(name = "date_of_birth", nullable = false, columnDefinition = "date")
    private LocalDate dateOfBirth;

    @EqualsAndHashCode.Include
    @Column(name = "phone", nullable = false, unique = true, length = 20)
    private String phone;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "remark", length = 1000)
    private String remark;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
