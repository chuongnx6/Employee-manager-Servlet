package dao.impl;

import dao.AccountDao;
import entity.Account;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class AccountDaoImpl implements AccountDao {
    @Override
    public Account getByUserName(String userName) {
        Account result = null;
        if (userName != null) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                transaction = session.beginTransaction();
                String hql = "FROM Account a " +
                        "WHERE a.userName = :userName";
                Query<Account> query = session.createQuery(hql, getEntityClass());
                query.setParameter("userName", userName);
                result = query.uniqueResult();
            } catch (HibernateException he) {
                throw he;
            }
        }
        return result;
    }

    @Override
    public Account getByEmail(String email) {
        Account result = null;
        if (email != null) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                transaction = session.beginTransaction();
                String hql = "FROM Account a " +
                        "WHERE a.email = :email";
                Query<Account> query = session.createQuery(hql, getEntityClass());
                query.setParameter("email", email);
                result = query.uniqueResult();
            } catch (HibernateException he) {
                throw he;
            }
        }
        return result;
    }
}
