package fa.training.dao;

import fa.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

/**
 * @param <E> the type of entity
 * @param <ID> the type of id field
 * */
public interface EntityDao<E, ID extends Serializable> {
    Class<E> getEntityClass();

    default E create(E e) {
        E result = null;
        if (e != null) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                transaction = session.beginTransaction();
                Serializable entityId = session.save(e);
                transaction.commit();
                result = session.get(getEntityClass(), entityId);
            } catch (HibernateException he) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw he;
            }
        }
        return result;
    }

    default E getById(ID id) {
        E result = null;
        if (id != null) {
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                transaction = session.beginTransaction();
                result = session.get(getEntityClass(), id);
            } catch (HibernateException he) {
                throw he;
            }
        }
        return result;
    }

    default List<E> getAll() {
        List<E> result = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            String hql = String.format("FROM %s", getEntityClass().getSimpleName());
            Query<E> query = session.createQuery(hql, getEntityClass());
            result = query.getResultList();
        } catch (HibernateException he) {
            throw he;
        }
        return result;
    }

    default boolean update(E e) {
        boolean result = false;
        Transaction transaction = null;
        if (e != null) {
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                transaction = session.beginTransaction();
                session.update(e);
                transaction.commit();
                result = true;
            } catch (HibernateException he) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw he;
            }
        }
        return result;
    }

    default boolean deleteById(ID id) {
        boolean result = false;
        Transaction transaction = null;
        if (id != null) {
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                transaction = session.beginTransaction();
                E e = session.get(getEntityClass(), id);
                if (e != null) {
                    session.delete(e);
                    transaction.commit();
                    result = true;
                }
            } catch (HibernateException he) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw he;
            }
        }
        return result;
    }
}
