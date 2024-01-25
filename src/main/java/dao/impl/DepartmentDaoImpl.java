package dao.impl;

import dao.DepartmentDao;
import entity.Department;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class DepartmentDaoImpl implements DepartmentDao {
    @Override
    public Department getByName(String name) {
        Department result = null;
        if (name != null) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                transaction = session.beginTransaction();
                String hql = "FROM Department d " +
                        "WHERE d.name = :name";
                Query<Department> query = session.createQuery(hql, getEntityClass());
                query.setParameter("name", name);
                result = query.uniqueResult();
            } catch (HibernateException he) {
                throw he;
            }
        }
        return result;
    }
}
