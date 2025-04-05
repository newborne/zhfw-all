package com.easyz.zhfw.handler;

import com.easyz.zhfw.pojo.AccountInfo;
import com.easyz.zhfw.pojo.BanKInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaymentHandler extends DefaultHandler {
    private List<AccountInfo> accounts;
    private StringBuilder data;
    private AccountInfo currentAccount;
    private BanKInfo currentBank;
    private String xmlFilePath;

    public PaymentHandler(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
        accounts = new ArrayList<>();
        data = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data.setLength(0);
        if ("account".equals(localName) && "http://www.example.org/bankDB".equals(uri)) {
            currentAccount = new AccountInfo();
        } else if ("bank".equals(localName) && "http://www.example.org/bankDB".equals(uri)) {
            currentBank = new BanKInfo();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("accountID".equals(localName) && "http://www.example.org/bankDB".equals(uri)) {
            currentAccount.setAccountID(data.toString());
        } else if ("amount".equals(localName) && "http://www.example.org/bankDB".equals(uri)) {
            currentAccount.setAmount(Integer.parseInt(data.toString()));
        } else if ("account".equals(localName) && "http://www.example.org/bankDB".equals(uri)) {
            accounts.add(currentAccount);
            currentAccount = null;
        } else if ("bank".equals(localName) && "http://www.example.org/bankDB".equals(uri)) {
            currentBank.getAccount().addAll(accounts);
            System.out.println("Loaded bank with " + accounts.size() + " accounts.");
        }
    }

    public BanKInfo getBank() {
        return currentBank;
    }

    public void saveToXml(BanKInfo bankInfo) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            // 创建根元素
            Element rootElement = document.createElementNS("http://www.example.org/bankDB", "bank:bank");
            document.appendChild(rootElement);

            // 添加账户信息
            for (AccountInfo account : bankInfo.getAccount()) {
                Element accountElement = document.createElementNS("http://www.example.org/bankDB", "bank:account");
                accountElement.appendChild(createElementWithText(document, "bank:accountID", account.getAccountID()));
                accountElement.appendChild(createElementWithText(document, "bank:amount", String.valueOf(account.getAmount())));
                rootElement.appendChild(accountElement);
            }

            // 写入文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(new File(xmlFilePath)));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element createElementWithText(Document document, String tagName, String text) {
        Element element = document.createElementNS("http://www.example.org/bankDB", tagName);
        element.setTextContent(text);
        return element;
    }
}
