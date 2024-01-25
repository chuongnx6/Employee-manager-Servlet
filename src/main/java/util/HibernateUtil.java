package fa.training.util;

import fa.training.entity.Candidate;
import fa.training.entity.EntryTest;
import fa.training.entity.Interview;
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

        cfg.setProperties(prop);

        cfg.addAnnotatedClass(Candidate.class);
        cfg.addAnnotatedClass(Interview.class);
        cfg.addAnnotatedClass(EntryTest.class);

        sessionFactory = cfg.buildSessionFactory();
    }
}
