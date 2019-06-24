/*
package com.bookkeeping.librarymanagement.controller;

import com.bookkeeping.librarymanagement.entity.Book;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

public class BookControllerTest1 extends AbstractTest {

    @Test
    public void getBookById() throws Exception {
        String uri = "/book/{id}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Book book = super.mapFromJson(content, Book.class);
        //assertTrue(book. > 0);
    }
}
*/
