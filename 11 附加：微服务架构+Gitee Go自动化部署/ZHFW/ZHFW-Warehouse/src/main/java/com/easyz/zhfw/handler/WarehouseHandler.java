package com.easyz.zhfw.handler;

import com.easyz.zhfw.pojo.HoldingRequestInfo;
import com.easyz.zhfw.pojo.ItemInfo;
import com.easyz.zhfw.pojo.ItemList;
import com.easyz.zhfw.pojo.WareHouse;
import com.easyz.zhfw.pojo.HoldingRequestList;
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

public class WarehouseHandler extends DefaultHandler {

    private WareHouse wareHouse;
    private ItemList itemList;
    private HoldingRequestList holdingRequestList;
    private ItemInfo currentItem;
    private HoldingRequestInfo currentRequest;
    private StringBuilder data;
    private boolean inItemsSection;
    private boolean inHoldingRequestsSection;
    private String xmlFilePath;

    public WarehouseHandler(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    @Override
    public void startDocument() throws SAXException {
        wareHouse = new WareHouse();
        itemList = new ItemList();
        holdingRequestList = new HoldingRequestList();
        data = new StringBuilder();
        inItemsSection = false;
        inHoldingRequestsSection = false;
    }

    @Override
    public void endDocument() throws SAXException {
        wareHouse.setItems(itemList);
        wareHouse.setHoldingReques(holdingRequestList);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        data.setLength(0);
        switch (localName) {
            case "items":
                inItemsSection = true;
                break;
            case "holdingReques":
                inHoldingRequestsSection = true;
                break;
            case "item":
                if (inItemsSection) {
                    currentItem = new ItemInfo();
                } else if (inHoldingRequestsSection) {
                    currentItem = new ItemInfo();
                }
                break;
            case "request":
                if (inHoldingRequestsSection) {
                    currentRequest = new HoldingRequestInfo();
                }
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        String value = data.toString().trim();
        switch (localName) {
            case "resourceID":
                if (currentItem != null) {
                    currentItem.setResourceID(value);
                }
                break;
            case "amount":
                if (currentItem != null) {
                    currentItem.setAmount(Integer.parseInt(value));
                    if (inItemsSection) {
                        itemList.getItem().add(currentItem);
                        currentItem = null; // 重置 currentItem
                    } else if (inHoldingRequestsSection && currentRequest != null) {
                        currentRequest.setItem(currentItem);
                        holdingRequestList.getRequest().add(currentRequest);
                        currentRequest = null; // 重置 currentRequest
                        currentItem = null; // 重置 currentItem
                    }
                }
                break;
            case "requestID":
                if (currentRequest != null) {
                    currentRequest.setRequestID(value);
                }
                break;
            case "items":
                inItemsSection = false;
                break;
            case "holdingReques":
                inHoldingRequestsSection = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(ch, start, length);
    }

    public WareHouse getWareHouse() {
        return wareHouse;
    }

    public void saveToXml() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            // 创建根元素
            Element rootElement = document.createElementNS("http://www.example.org/warehouse", "warehouse");
            document.appendChild(rootElement);

            // 添加 items 元素
            Element itemsElement = document.createElementNS("http://www.example.org/warehouse", "items");
            rootElement.appendChild(itemsElement);
            for (ItemInfo item : wareHouse.getItems().getItem()) {
                Element itemElement = document.createElementNS("http://www.example.org/warehouse", "item");
                itemElement.appendChild(createElementWithText(document, "resourceID", item.getResourceID()));
                itemElement.appendChild(createElementWithText(document, "amount", String.valueOf(item.getAmount())));
                itemsElement.appendChild(itemElement);
            }

            // 添加 holdingReques 元素
            Element holdingRequesElement = document.createElementNS("http://www.example.org/warehouse", "holdingReques");
            rootElement.appendChild(holdingRequesElement);
            for (HoldingRequestInfo request : wareHouse.getHoldingReques().getRequest()) {
                Element requestElement = document.createElementNS("http://www.example.org/warehouse", "request");
                requestElement.appendChild(createElementWithText(document, "requestID", request.getRequestID()));
                Element itemElement = document.createElementNS("http://www.example.org/warehouse", "item");
                itemElement.appendChild(createElementWithText(document, "resourceID", request.getItem().getResourceID()));
                itemElement.appendChild(createElementWithText(document, "amount", String.valueOf(request.getItem().getAmount())));
                requestElement.appendChild(itemElement);
                holdingRequesElement.appendChild(requestElement);
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
        Element element = document.createElementNS("http://www.example.org/warehouse", tagName);
        element.setTextContent(text);
        return element;
    }
}
