package util;

import entity.Account;
import entity.Department;
import entity.Employee;
import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;

public class HibernateUtil {
    @Getter
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        Properties prop = new Properties();
        prop.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "org.hibernate.context.internal.ThreadLocalSessionContext");
        prop.put(Environment.SHOW_SQL, "true");
        prop.put(Environment.FORMAT_SQL, "true");
        prop.put(Environment.HBM2DDL_AUTO, "update");
        prop.put(Environment.USE_LEGACY_LIMIT_HANDLERS,"true");

        cfg.setProperties(prop);

        cfg.addAnnotatedClass(Account.class);
        cfg.addAnnotatedClass(Department.class);
        cfg.addAnnotatedClass(Employee.class);

        sessionFactory = cfg.buildSessionFactory();
    }
}
