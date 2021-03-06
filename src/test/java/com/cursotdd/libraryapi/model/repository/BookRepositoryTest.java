package com.cursotdd.libraryapi.model.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.cursotdd.libraryapi.model.entity.Book;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest // ? indica que irei fazer testes com JPA, ele ira criar uma instância do banco de dados em memória
public class BookRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com o isbn informado")
    public void returnTrueWhenIsbnExists() {
        // ? cenario
        String isbn = "123";
        Book book = Book.builder().title("Aventuras").author("Fluano").isbn(isbn).build();
        entityManager.persist(book);

        // ? execução
        boolean exists = repository.existsByIsbn(isbn);

        // ? verificação
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um livro na base com o isbn informado")
    public void returnFalseWhenIsbnDoenstExists() {
        // ? cenario
        String isbn = "123";

        // ? execução
        boolean exists = repository.existsByIsbn(isbn);

        // ? verificação
        assertThat(exists).isFalse();
    }
}
