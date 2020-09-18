package com.cursotdd.libraryapi.service;

import com.cursotdd.libraryapi.exception.BusinessException;
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
        final Book book = createValidBook();
        // ? Estamos simulando um ambiente de teste com o mockito.when, aonde dizemos que quando o método save do repository for executado recebendo um instância de book, o mesmo deve retorna um book com um id populado (Nós simulamos tudo isso!!!)
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(false);
        Mockito.when( repository.save(book) ).thenReturn(Book.builder().id(1l).isbn("123").author("Fulano").title("As aventuras").build());

        // execução
        final Book savedBook = service.save(book);
        
        // verificação
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals(savedBook.getIsbn(), "123");
        Assertions.assertEquals(savedBook.getTitle(), "As aventuras");
        Assertions.assertEquals(savedBook.getAuthor(), "Fulano");

    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
    public void shouldNotSaveBookWithDuplicatedISBN() {
        final Book book = createValidBook();
        // * Quando o existsByIsbn for executado durante esse meu teste, passando qualquer string, deve retorna true
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);

        // ? Execução
       Throwable exeception = org.assertj.core.api.Assertions.catchThrowable(() -> service.save(book));
       // ? Verificações
       org.assertj.core.api.Assertions.assertThat(exeception)
       .isInstanceOf(BusinessException.class)
       .hasMessage("Isbn já cadastrado.");
       Mockito.verify(repository, Mockito.never()).save(book);
    }


    public Book createValidBook() {
        return Book.builder().isbn("123").author("Fulano").title("As Aventuras").build();
    }
}