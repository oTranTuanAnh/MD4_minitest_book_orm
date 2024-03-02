package com.example.bookorm.controller;

import com.example.bookorm.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("books")
public class BookController {
    @Autowired
    private IBookService bookService;
    @GetMapping("")
    public String home(){
        bookService.findAll();

        return "index";
    }
}
