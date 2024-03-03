package com.example.bookorm.service;

import com.example.bookorm.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> findAll();

    void save(Book book);

    Book findById(int id);

    void remove(int id);
    void update(Book book);
}
