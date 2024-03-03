package com.example.bookorm.service;

import com.example.bookorm.model.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class BookService implements IBookService {
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT b FROM Book AS b";
        TypedQuery<Book> typedQuery = entityManager.createQuery(query, Book.class);
        return typedQuery.getResultList();

    }

    @Override
    public void save(Book book) {
        Transaction transaction = null;
        Book origin;
        if (book.getId() == 0) {
            origin = new Book();
        } else {
            origin = findById(book.getId());
        }
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            origin.setName(book.getName());
            origin.setAuthor(book.getAuthor());
            origin.setPrice(book.getPrice());
            origin.setImg(book.getImg());
            session.saveOrUpdate(origin);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Book findById(int id) {
        String query = "SELECT b FROM Book AS b WHERE b.id = :id";
        TypedQuery<Book> typedQuery = entityManager.createQuery(query, Book.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Override
    public void remove(int id) {
        entityManager.getTransaction().begin();
        String queryStr = "DELETE FROM Book AS b WHERE b.id = :id";
        Query query = entityManager.createQuery(queryStr);
        query.setParameter("id", id);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}
