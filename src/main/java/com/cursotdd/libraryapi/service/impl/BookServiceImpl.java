package com.cursotdd.libraryapi.service.impl;

import com.cursotdd.libraryapi.exception.BusinessException;
import com.cursotdd.libraryapi.model.entity.Book;
import com.cursotdd.libraryapi.model.repository.BookRepository;
import com.cursotdd.libraryapi.service.BookService;

import org.springframework.stereotype.Service;

@Service // gerenciador pelo spring framework como um serviço
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        if (repository.existsByIsbn(book.getIsbn())) {
            throw new BusinessException("Isbn já cadastrado.");
        }
        return repository.save(book);
    }
    


}