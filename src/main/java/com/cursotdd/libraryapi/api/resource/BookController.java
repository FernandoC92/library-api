package com.cursotdd.libraryapi.api.resource;


import javax.validation.Valid;

import com.cursotdd.libraryapi.api.dto.BookDto;
import com.cursotdd.libraryapi.exception.ApiErrors;
import com.cursotdd.libraryapi.exception.BusinessException;
import com.cursotdd.libraryapi.model.entity.Book;
import com.cursotdd.libraryapi.service.BookService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private BookService service;
    private ModelMapper modelMapper;

    public BookController(BookService service, ModelMapper mapper) {
        this.service = service;
        this.modelMapper = mapper;
    }

    @GetMapping
    public String myMethod() {
        return "Testando 1";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // * setado para 201: Created
    public BookDto create( @RequestBody @Valid BookDto dto ) { // ? vai validar o BookDto de acordo com as notations de validação usadas na classe
        
        Book entity = modelMapper.map(dto, Book.class);
        entity = service.save(entity);
        return modelMapper.map(entity, BookDto.class);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        // List<ObjectError> allErrors = bindingResult.getAllErrors();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerBusinessException(BusinessException ex) {
        return new ApiErrors(ex);
    }
    
}