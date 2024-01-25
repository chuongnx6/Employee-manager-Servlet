package dao.impl;

import dao.EmployeeDao;
import entity.Account;
import entity.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public List<Employee> getByAccountIdAndKeyword(int accountId, String keyword, String filterBy, int pageNumber, int pageSize) {
        List<Employee> result = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            Account account = Account.builder().id(accountId).build();
            String hql1 = "FROM Employee e " +
                    "WHERE e.account = :accountId ";
            String hql2;
            switch (filterBy) {
                case "name":
                    hql2 = "AND concat(e.firstName, ' ', e.lastName) LIKE :keyword";
                    break;
                case "address":
                    hql2 = "AND e.address LIKE :keyword";
                    break;
                case "phone":
                    hql2 = "AND e.phone LIKE :keyword";
                    break;
                case "department":
                    hql2 = "AND e.department LIKE :keyword";
                    break;
                default:
                    return result;
            }
            String hql = hql1 + hql2;
            Query<Employee> query = session.createQuery(hql, getEntityClass());
            query.setParameter("accountId", account);
            query.setParameter("keyword", "%"+ keyword +"%");
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            result = query.getResultList();
        } catch (HibernateException he) {
            throw he;
        }
        return result;
    }

    @Override
    public long countResultByAccountIdAndKeyword(int accountId, String keyword, String filterBy, int pageNumber, int pageSize) {
        long result = 0;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            Account account = Account.builder().id(accountId).build();
            String hql1 = "SELECT COUNT(e.id) " +
                    "FROM Employee e " +
                    "WHERE e.account = :accountId ";
            String hql2;
            switch (filterBy) {
                case "name":
                    hql2 = "AND concat(e.firstName, ' ', e.lastName) LIKE :keyword";
                    break;
                case "address":
                    hql2 = "AND e.address LIKE :keyword";
                    break;
                case "phone":
                    hql2 = "AND e.phone LIKE :keyword";
                    break;
                case "department":
                    hql2 = "AND e.department LIKE :keyword";
                    break;
                default:
                    return result;
            }
            String hql = hql1 + hql2;
            Query query = session.createQuery(hql);
            query.setParameter("accountId", account);
            query.setParameter("keyword", "%"+ keyword +"%");
            result = (long) query.getSingleResult();
        } catch (HibernateException he) {
            throw he;
        }
        return result;
    }
}
