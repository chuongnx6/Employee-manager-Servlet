import dao.AccountDao;
import dao.DepartmentDao;
import dao.EmployeeDao;
import dao.impl.AccountDaoImpl;
import dao.impl.DepartmentDaoImpl;
import dao.impl.EmployeeDaoImpl;
import entity.Account;
import entity.Department;
import entity.Employee;
import entity.enumeration.Gender;
import util.EncryptionUtil;

import java.time.LocalDate;
import java.util.Random;

public class Database {
    private static AccountDao accountDao = new AccountDaoImpl();
    private static DepartmentDao departmentDao = new DepartmentDaoImpl();
    private static EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static String[] firstNames = {
            "Alice", "Bobert", "Charlize", "Davidson", "Emmaline", "Franklin", "Gracelyn", "Henderson", "Ivory", "Jackson",
            "Katherine", "Liamson", "Miaella", "Noahson", "Olivianna", "Peterick", "Quinnton", "Rachelle", "Samuela", "Taylora"
    };
    private static String[] lastNames = {
            "Adamson", "Brownton", "Clarkson", "Davisson", "Evanson", "Fisherson", "Garciason", "Hallington", "Irwinson", "Joneson",
            "Khanson", "Lopezon", "Millerson", "Nguyenson", "Owenson", "Perezson", "Quinnson", "Reyesson", "Smithson", "Thomason"
    };
    private static String[] addresses = {
            "123 Database Street, Suite 567, Cityville, State, 12345",
            "456 Elm Street, Apt 203, Townsville, State, 67890",
            "789 Oak Avenue, Unit 45, Villageton, State, 34567",
            "101 Pine Street, Building 12, Hamletown, State, 89012",
            "555 Maple Avenue, Floor 3, Countryside, State, 23456",
            "321 Cedar Drive, Apt 1020, Metroville, State, 56789",
            "888 Birch Lane, Unit 789, Suburbia, State, 01234",
            "234 Walnut Boulevard, Suite 456, Townburg, State, 67890",
            "777 Spruce Road, Apt 304, Villagetown, State, 12345",
            "444 Sycamore Court, Building 78, Cityburg, State, 56789",
            "890 Pinecone Way, Floor 5, Villasville, State, 23456",
            "111 Redwood Place, Apt 678, Countryside, State, 89012",
            "555 Aspen Circle, Suite 901, Hamletville, State, 34567",
            "987 Juniper Street, Unit 234, Suburbville, State, 01234",
            "222 Acorn Avenue, Building 56, Townsville, State, 67890"
    };
    private static String[] departments = {"Engineering", "Marketing", "Finance", "HR", "Sales", "IT", "Operations"};

    public static void main(String[] args) {
        saveAccount();
        saveDepartment();
        saveEmployee(100, 1);
        saveEmployee(60, 2);
        saveEmployee(30, 3);
    }

    private static void saveAccount() {
        Account admin = Account.builder()
                .email("admin@gmail.com")
                .userName("admin")
                .password(EncryptionUtil.encrypt("admin"))
                .status(1)
                .build();
        Account admin1 = Account.builder()
                .email("admin1@gmail.com")
                .userName("admin1")
                .password(EncryptionUtil.encrypt("admin1"))
                .status(1)
                .build();
        Account admin2 = Account.builder()
                .email("admin2@gmail.com")
                .userName("admin2")
                .password(EncryptionUtil.encrypt("admin2"))
                .status(1)
                .build();
        Account user = Account.builder()
                .email("user@gmail.com")
                .userName("user")
                .password(EncryptionUtil.encrypt("user"))
                .status(1)
                .build();
        accountDao.create(admin);
        accountDao.create(admin1);
        accountDao.create(admin2);
        accountDao.create(user);
    }

    private static void saveDepartment() {
        for (String departmentName : departments) {
            Department department = Department.builder()
                    .name(departmentName)
                    .build();
            departmentDao.create(department);
        }
    }

    private static void saveEmployee(int totalEmployee, int accountId) {
        Random rd = new Random();
        Account account = accountDao.getById(accountId);
        for (int i = 0; i < totalEmployee; i++) {
            Employee employee = Employee.builder()
                    .firstName(firstNames[rd.nextInt(firstNames.length)])
                    .lastName(lastNames[rd.nextInt(lastNames.length)])
                    .gender(rd.nextInt(10) % 2 == 0 ? Gender.MALE : Gender.FEMALE)
                    .dateOfBirth(LocalDate.of(rd.nextInt(15) + 1990, rd.nextInt(12) + 1, rd.nextInt(28) + 1))
                    .phone(generatePhoneNumber())
                    .address(addresses[rd.nextInt(addresses.length)])
                    .department(departmentDao.getById(rd.nextInt(departments.length) + 1))
                    .remark("Remark " + i)
                    .account(account)
                    .build();
            employeeDao.create(employee);
        }
    }

    private static String generatePhoneNumber() {
        Random rd = new Random();
        StringBuilder sb = new StringBuilder("0");
        sb.append(rd.nextInt(8) + 2);
        for (int i = 0; i < 8; i++) {
            sb.append(rd.nextInt(10));
        }
        return sb.toString();
    }
}
