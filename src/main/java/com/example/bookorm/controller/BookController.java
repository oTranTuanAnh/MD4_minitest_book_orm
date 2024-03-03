package com.example.bookorm.controller;

import com.example.bookorm.model.Book;
import com.example.bookorm.model.BookForm;
import com.example.bookorm.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.IOException;

@Controller
@EnableWebMvc
@RequestMapping("books")
@PropertySource("classpath:upload_file.properties")
public class BookController {
    @Autowired
    private IBookService bookService;
    @Value("${upload}")
    private String upload;
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("books", bookService.findAll());
        return "index";
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("book", new BookForm());
        return "create";
    }
    @PostMapping("/save")
    public String save(BookForm bookForm) throws IOException {
        MultipartFile multipartFile = bookForm.getImg();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(multipartFile.getBytes(), new File(upload+fileName));

        Book book = new Book();
        book.setName(bookForm.getName());
        book.setAuthor(bookForm.getAuthor());
        book.setPrice(bookForm.getPrice());
        book.setImg(fileName);
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model){
        model.addAttribute("books", bookService.findById(id));
        return "delete";
    }
    @PostMapping("/delete")
    public String remove(Book book){
        bookService.remove(book.getId());
        return "redirect:/books";
    }
    @GetMapping("/{id}/view")
    public String detail(@PathVariable int id, Model model){
        model.addAttribute("books", bookService.findById(id));
        return "view";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("books", bookService.findById(id));
        return "edit";
    }
    @PostMapping("/update")
    public String update(BookForm bookForm, Model model) throws IOException {
        MultipartFile multipartFile = bookForm.getImg();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(multipartFile.getBytes(), new File(upload+fileName));

        Book book = new Book();
        book.setId(bookForm.getId());
        book.setName(bookForm.getName());
        book.setAuthor(bookForm.getAuthor());
        book.setPrice(bookForm.getPrice());
        book.setImg(fileName);
//        bookService.update(book);
        bookService.save(book);


        return "redirect:/books";

    }



}
