package com.cursotdd.libraryapi.service;

import com.cursotdd.libraryapi.model.entity.Book;
import com.cursotdd.libraryapi.model.repository.BookRepository;
import com.cursotdd.libraryapi.service.impl.BookServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {
    
    BookService service;
    @MockBean
    BookRepository repository;

    @BeforeEach // executa antes de cada teste
    public void setUp() {
        this.service = new BookServiceImpl( repository );
    }

    @Test
    @DisplayName("Deve salvar um livro.")
    public void saveBookTest() {    
        // cenario
        Book book = Book.builder().isbn("123").author("Fulano").title("As Aventuras").build();
        // ? Estamos simulando um ambiente de teste com o mockito.when, aonde dizemos que quando o método save do repository for executado recebendo um instância de book, o mesmo deve retorna um book com um id populado (Nós simulamos tudo isso!!!)
        
        Mockito.when( repository.save(book) ).thenReturn(Book.builder().id(1l).isbn("123").author("Fulano").title("As aventuras").build());

        // execução
        Book savedBook = service.save(book);
        
        // verificação
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals(savedBook.getIsbn(), "123");
        Assertions.assertEquals(savedBook.getTitle(), "As aventuras");
        Assertions.assertEquals(savedBook.getAuthor(), "Fulano");

    }

}