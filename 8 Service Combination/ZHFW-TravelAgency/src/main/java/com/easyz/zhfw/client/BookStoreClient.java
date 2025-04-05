package com.easyz.zhfw.client;

import com.easyz.zhfw.pojo.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class BookStoreClient {

    private static final String BASE_URL = "http://zhfw-bookstore:8082";

    @Autowired
    private RestTemplate restTemplate;

    public List<String> getAllBookNames() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(BASE_URL + "/getAllBookNames", String[].class);
        return Arrays.asList(response.getBody());
    }

    public List<String> getAllBookISBN10() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(BASE_URL + "/getAllBookISBN10", String[].class);
        return Arrays.asList(response.getBody());
    }

    public List<String> getAllBookISBN13() {
        ResponseEntity<String[]> response = restTemplate.getForEntity(BASE_URL + "/getAllBookISBN13", String[].class);
        return Arrays.asList(response.getBody());
    }

    public List<BookInfo> getBooksByTitle(String title) {
        ResponseEntity<BookInfo[]> response = restTemplate.getForEntity(BASE_URL + "/getBooksByTitle?title={title}", BookInfo[].class, title);
        return Arrays.asList(response.getBody());
    }

    public List<BookInfo> getBooksByAuthor(String author) {
        ResponseEntity<BookInfo[]> response = restTemplate.getForEntity(BASE_URL + "/getBooksByAuthor?author={author}", BookInfo[].class, author);
        return Arrays.asList(response.getBody());
    }

    public BookInfo getBookByISBN10(String isbn10) {
        ResponseEntity<BookInfo> response = restTemplate.getForEntity(BASE_URL + "/getBookInfobyISBN10?isbn10={isbn10}", BookInfo.class, isbn10);
        return response.getBody();
    }

    public BookInfo getBookByISBN13(String isbn13) {
        ResponseEntity<BookInfo> response = restTemplate.getForEntity(BASE_URL + "/getBookInfobyISBN13?isbn13={isbn13}", BookInfo.class, isbn13);
        return response.getBody();
    }
}
