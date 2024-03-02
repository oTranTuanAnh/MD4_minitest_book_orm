package com.example.bookorm.service;

import com.example.bookorm.model.Book;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
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
        return null;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public Book findById(int id) {
        return null;
    }

    @Override
    public void remove(int id) {

    }
}
