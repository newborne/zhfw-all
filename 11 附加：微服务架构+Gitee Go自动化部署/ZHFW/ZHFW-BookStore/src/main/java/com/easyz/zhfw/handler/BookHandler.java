package com.easyz.zhfw.handler;

import com.easyz.zhfw.pojo.AuthorsInfo;
import com.easyz.zhfw.pojo.BookInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class BookHandler extends DefaultHandler {
    // 成员变量
    private List<BookInfo> books;
    private BookInfo currentBook;
    private StringBuilder data;
    private boolean isAuthor;

    // 构造方法
    public BookHandler() {
        books = new ArrayList<>();
        data = new StringBuilder();
    }

    // 获取书籍列表
    public List<BookInfo> getBooks() {
        return books;
    }

    // 文档开始
    @Override
    public void startDocument() throws SAXException {
        books.clear();
    }

    // 文档结束
    @Override
    public void endDocument() throws SAXException {
        // 文档解析结束
    }

    // 元素开始
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data.setLength(0); // 清空StringBuilder
        if ("book".equalsIgnoreCase(localName)) {
            currentBook = new BookInfo();
            currentBook.setAuthors(new AuthorsInfo());
        } else if ("authors".equalsIgnoreCase(localName)) {
            isAuthor = true;
        }
    }

    // 文本内容
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(ch, start, length);
    }

    // 元素结束
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("book".equalsIgnoreCase(localName)) {
            books.add(currentBook);
        } else if ("title".equalsIgnoreCase(localName)) {
            currentBook.setTitle(data.toString().trim());
        } else if ("resourceID".equalsIgnoreCase(localName)) {
            currentBook.setResourceID(data.toString().trim());
        } else if ("ISBN13".equalsIgnoreCase(localName)) {
            currentBook.setISBN13(data.toString().trim());
        } else if ("ISBN10".equalsIgnoreCase(localName)) {
            currentBook.setISBN10(data.toString().trim());
        } else if ("pageNum".equalsIgnoreCase(localName)) {
            currentBook.setPageNum(Integer.parseInt(data.toString().trim()));
        } else if ("publisher".equalsIgnoreCase(localName)) {
            currentBook.setPublisher(data.toString().trim());
        } else if ("publishdate".equalsIgnoreCase(localName)) {
            currentBook.setPublishdate(data.toString().trim());
        } else if ("version".equalsIgnoreCase(localName)) {
            currentBook.setVersion(data.toString().trim());
        } else if ("price".equalsIgnoreCase(localName)) {
            currentBook.setPrice(Double.parseDouble(data.toString().trim()));
        } else if (isAuthor && "author".equalsIgnoreCase(localName)) {
            // 获取当前书的作者列表，并添加作者
            ArrayList<String> authorList = currentBook.getAuthors().getAuthorlist();
            if (authorList == null) {
                authorList = new ArrayList<>();
                currentBook.getAuthors().setAuthorlist(authorList);
            }
            authorList.add(data.toString().trim());
        }
        if ("authors".equalsIgnoreCase(localName)) {
            isAuthor = false;
        }
    }
}
