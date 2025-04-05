package com.easyz.zhfw.service.impl;

import com.easyz.zhfw.handler.BookHandler;
import com.easyz.zhfw.pojo.BookInfo;
import com.easyz.zhfw.service.BookCategoryWS;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookCategoryWSImpl implements BookCategoryWS {

    private final String xmlFilePath = "datasource/ds_2_1.xml"; // XML 文件路径

    private List<BookInfo> bookList;

    // 加载书籍列表
    private void loadBooks() {
        try {
            // 创建SAX解析器
            XMLReader reader = XMLReaderFactory.createXMLReader();
            BookHandler handler = new BookHandler();
            reader.setContentHandler(handler);

            // 解析XML文件
            reader.parse(new InputSource(new File(xmlFilePath).toURI().toString()));

            bookList = handler.getBooks();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            bookList = Collections.emptyList();
        }
    }

    /**
     * Get the names of all the books
     *
     * @return the list of names for all the books
     */
    @Override
    public List<String> getAllBooKNames() {
        if (bookList == null) {
            loadBooks();
        }
        return bookList.stream()
                .map(BookInfo::getTitle)
                .collect(Collectors.toList());
    }

    /**
     * Get the ISBN10 codings of all the books
     *
     * @return the list of ISBN10 codings for all the books
     */
    @Override
    public List<String> getAllBookISBN10() {
        if (bookList == null) {
            loadBooks();
        }
        return bookList.stream()
                .map(BookInfo::getISBN10)
                .collect(Collectors.toList());
    }

    /**
     * Get the ISBN13 codings of all the books
     *
     * @return the list of ISBN13 codings for all the books
     */
    @Override
    public List<String> getAllBookISBN13() {
        if (bookList == null) {
            loadBooks();
        }
        return bookList.stream()
                .map(BookInfo::getISBN13)
                .collect(Collectors.toList());
    }

    /**
     * Get all the books of a title
     *
     * @param title the title of the books
     * @return the list of book info with given title
     */
    @Override
    public List<BookInfo> getBooksByTitle(String title) {
        if (bookList == null) {
            loadBooks();
        }
        return bookList.stream()
                .filter(book -> title.equals(book.getTitle()))
                .collect(Collectors.toList());
    }

    /**
     * Get all the books of an author
     *
     * @param author the author of the books
     * @return the list of books written by the given author
     */
    @Override
    public List<BookInfo> getBooksByAuthor(String author) {
        if (bookList == null) {
            loadBooks();
        }
        return bookList.stream()
                .filter(book -> book.getAuthors().getAuthorlist().stream().anyMatch(a -> a.equals(author)))
                .collect(Collectors.toList());
    }

    /**
     * Get a book info by ISBN10
     *
     * @param isbn10 the ISBN10 coding of the book
     * @return the book with the given ISBN10 coding
     */
    @Override
    public BookInfo getBookInfobyISBN10(String isbn10) {
        if (bookList == null) {
            loadBooks();
        }
        return bookList.stream()
                .filter(book -> isbn10.equals(book.getISBN10()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a book info by ISBN13
     *
     * @param isbn13 the ISBN13 coding of the book
     * @return the book with the given ISBN13 coding
     */
    @Override
    public BookInfo getBookInfobyISBN13(String isbn13) {
        if (bookList == null) {
            loadBooks();
        }
        return bookList.stream()
                .filter(book -> isbn13.equals(book.getISBN13()))
                .findFirst()
                .orElse(null);
    }
}
