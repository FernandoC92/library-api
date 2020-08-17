package com.cursotdd.libraryapi.api.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cursotdd.libraryapi.model.entity.Book;

import com.cursotdd.libraryapi.api.dto.BookDto;
import com.cursotdd.libraryapi.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest // testando o comporatamento da api
@AutoConfigureMockMvc
public class BookControllerTest {

    static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mvc;

    @MockBean // Mocka a instãncia da classe e faz a injeção de depedência
    BookService service;

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    public void createBookTest() throws Exception {

        BookDto dto = BookDto.builder().author("Artur").title("As Aventuras").isbn("001").build();
        Book savedBook = Book.builder().id(10l).author("Artur").title("As Aventuras").isbn("001").build();
        // ? Estamos apenas testando o designer da api, BDDMockito se encarrega da simulação
        BDDMockito.given(service.save(Mockito.any(Book.class))).willReturn(savedBook);
        String json = new ObjectMapper().writeValueAsString(dto);

         MockHttpServletRequestBuilder request = MockMvcRequestBuilders
         .post(BOOK_API)
         .contentType(MediaType.APPLICATION_JSON)
         .accept(MediaType.APPLICATION_JSON)
         .content(json);

         mvc
            .perform(request)
            .andExpect( status().isCreated() ) // Todos esses métodos são de MockMvcResultMatchers: status(), jsonPath()...
            .andExpect( jsonPath("id").value(10l))
            .andExpect( jsonPath("title").value(dto.getTitle()) )
            .andExpect( jsonPath("author").value(dto.getAuthor()) )
            .andExpect(jsonPath("isbn").value(dto.getIsbn()));

    }

    
    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes para criação do livro.")
    public void createInvalidBookTest() {
        
    }

}



