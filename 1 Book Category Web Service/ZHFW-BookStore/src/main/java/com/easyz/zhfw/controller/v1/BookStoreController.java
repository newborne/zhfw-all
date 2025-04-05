package com.easyz.zhfw.controller.v1;

import com.easyz.zhfw.pojo.BookInfo;
import com.easyz.zhfw.service.BookCategoryWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class BookStoreController {

    private final BookCategoryWS bookService;

    @Autowired
    public BookStoreController(BookCategoryWS bookService) {
        this.bookService = bookService;
    }

    /**
     * 获取所有书籍的名称
     *
     * @return 所有书籍的名称列表
     */
    @GetMapping("/getAllBookNames")
    public List<String> getAllBookNames() {
        System.out.println("123");
        return bookService.getAllBooKNames();
    }

    /**
     * 获取所有书籍的 ISBN10 编码
     *
     * @return 所有书籍的 ISBN10 编码列表
     */
    @GetMapping("/getAllBookISBN10")
    public List<String> getAllBookISBN10() {
        return bookService.getAllBookISBN10();
    }

    /**
     * 获取所有书籍的 ISBN13 编码
     *
     * @return 所有书籍的 ISBN13 编码列表
     */
    @GetMapping("/getAllBookISBN13")
    public List<String> getAllBookISBN13() {
        return bookService.getAllBookISBN13();
    }

    /**
     * 根据书名获取书籍信息
     *
     * @param title 书名
     * @return 匹配的书籍信息列表
     */
    @GetMapping("/getBooksByTitle")
    public List<BookInfo> getBooksByTitle(@RequestParam String title) {
        return bookService.getBooksByTitle(title);
    }

    /**
     * 根据作者获取书籍信息
     *
     * @param author 作者
     * @return 匹配的书籍信息列表
     */
    @GetMapping("/getBooksByAuthor")
    public List<BookInfo> getBooksByAuthor(@RequestParam String author) {
        return bookService.getBooksByAuthor(author);
    }

    /**
     * 根据 ISBN10 获取书籍信息
     *
     * @param isbn10 ISBN10 编码
     * @return 匹配的书籍信息
     */
    @GetMapping("/getBookInfobyISBN10")
    public BookInfo getBookByISBN10(@RequestParam String isbn10) {
        return bookService.getBookInfobyISBN10(isbn10);
    }

    /**
     * 根据 ISBN13 获取书籍信息
     *
     * @param isbn13 ISBN13 编码
     * @return 匹配的书籍信息
     */
    @GetMapping("/getBookInfobyISBN13")
    public BookInfo getBookByISBN13(@RequestParam String isbn13) {
        return bookService.getBookInfobyISBN13(isbn13);
    }
}