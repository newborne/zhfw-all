package com.easyz.zhfw.service.impl;

import com.easyz.zhfw.handler.WarehouseHandler;
import com.easyz.zhfw.pojo.*;
import com.easyz.zhfw.service.WarehouseWS;
import com.easyz.zhfw.vo.InvalidHoldingIDException;
import com.easyz.zhfw.vo.NotEnoughItemException;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarehouseWSImpl implements WarehouseWS {

    private final String xmlFilePath = "datasource/ds_2_2.xml"; // XML 文件路径

    private WareHouse wareHouse;
    private WarehouseHandler warehouseHandler;

    // 构造函数中加载仓库数据
    public WarehouseWSImpl() {
        loadWarehouseData();
    }

    // 加载仓库数据
    private void loadWarehouseData() {
        try {
            // 创建SAX解析器
            XMLReader reader = XMLReaderFactory.createXMLReader();
            warehouseHandler = new WarehouseHandler(xmlFilePath);
            reader.setContentHandler(warehouseHandler);
            // 解析XML文件
            reader.parse(new InputSource(new File(xmlFilePath).toURI().toString()));
            wareHouse = warehouseHandler.getWareHouse();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            wareHouse = new WareHouse();
            wareHouse.setItems(new ItemList());
            wareHouse.getItems().getItem().addAll(Collections.emptyList());
            wareHouse.setHoldingReques(new HoldingRequestList());
            wareHouse.getHoldingReques().getRequest().addAll(Collections.emptyList());
        }
    }


    /**
     * 查询仓库中指定资源ID的物品数量
     *
     * @param resourceID 物品的资源ID
     * @return 仓库中该物品的数量
     */
    @Override
    public int query(String resourceID) {
        if (wareHouse.getItems() == null || wareHouse.getItems().getItem() == null) {
            loadWarehouseData();
        }
        Optional<ItemInfo> optionalItem = wareHouse.getItems().getItem().stream()
                .filter(item -> item.getResourceID().equals(resourceID))
                .findFirst();
        return optionalItem.map(ItemInfo::getAmount).orElse(0);
    }

    /**
     * 从仓库中移除指定数量的物品
     *
     * @param resourceID 物品的资源ID
     * @param amount 需要移除的物品数量
     * @return 操作成功返回true，否则返回false
     * @throws NotEnoughItemException 如果库存不足
     */
    @Override
    public boolean pickupItems(String resourceID, int amount) throws NotEnoughItemException {
        if (wareHouse.getItems() == null || wareHouse.getItems().getItem() == null) {
            loadWarehouseData();
        }
        Optional<ItemInfo> optionalItem = wareHouse.getItems().getItem().stream()
                .filter(item -> item.getResourceID().equals(resourceID))
                .findFirst();
        if (optionalItem.isPresent()) {
            ItemInfo item = optionalItem.get();
            if (item.getAmount() >= amount) {
                item.setAmount(item.getAmount() - amount);
                 warehouseHandler.saveToXml();// 保存数据
                return true;
            } else {
                throw new NotEnoughItemException("Not enough items in the warehouse");
            }
        }
        return false;
    }

    /**
     * 补充仓库中指定资源ID的物品数量
     *
     * @param resourceID 物品的资源ID
     * @param amount 需要补充的物品数量
     * @return 补充后仓库中该物品的新数量
     */
    @Override
    public int complementStock(String resourceID, int amount) {
        if (wareHouse.getItems() == null || wareHouse.getItems().getItem() == null) {
            loadWarehouseData();
        }
        Optional<ItemInfo> optionalItem = wareHouse.getItems().getItem().stream()
                .filter(item -> item.getResourceID().equals(resourceID))
                .findFirst();
        if (optionalItem.isPresent()) {
            ItemInfo item = optionalItem.get();
            item.setAmount(item.getAmount() + amount);
            warehouseHandler.saveToXml();// 保存数据
            return item.getAmount();
        }
        return 0;
    }

    /**
     * 预留一些物品以备后续使用。预留的物品在查询操作中不可见，只能通过预留请求ID访问
     *
     * @param resourceID 物品的资源ID
     * @param amount 需要预留的物品数量
     * @return 预留请求的ID（可用于提货或取消预留）
     * @throws NotEnoughItemException 如果库存不足
     */
    @Override
    public String holdItems(String resourceID, int amount) throws NotEnoughItemException {
        if (wareHouse.getItems() == null || wareHouse.getItems().getItem() == null) {
            loadWarehouseData();
        }
        Optional<ItemInfo> optionalItem = wareHouse.getItems().getItem().stream()
                .filter(item -> item.getResourceID().equals(resourceID))
                .findFirst();
        if (optionalItem.isPresent()) {
            ItemInfo item = optionalItem.get();
            if (item.getAmount() >= amount) {
                String holdingID = UUID.randomUUID().toString();
                HoldingRequestInfo holdingRequest = new HoldingRequestInfo();
                holdingRequest.setRequestID(holdingID);
                holdingRequest.setItem(new ItemInfo());
                holdingRequest.getItem().setResourceID(resourceID);
                holdingRequest.getItem().setAmount(amount);
                wareHouse.getHoldingReques().getRequest().add(holdingRequest);
                item.setAmount(item.getAmount() - amount);
                warehouseHandler.saveToXml();// 保存数据
                return holdingID;
            } else {
                throw new NotEnoughItemException("Not enough items in the warehouse");
            }
        }
        return null;
    }

    /**
     * 取消预留请求。预留的物品将被释放回仓库
     *
     * @param holdingID 预留请求的ID
     */
    @Override
    public void cancelHoldingItems(String holdingID) {
        if (wareHouse.getHoldingReques() == null || wareHouse.getHoldingReques().getRequest() == null) {
            loadWarehouseData();
        }
        Optional<HoldingRequestInfo> optionalRequest = wareHouse.getHoldingReques().getRequest().stream()
                .filter(request -> request.getRequestID().equals(holdingID))
                .findFirst();
        if (optionalRequest.isPresent()) {
            HoldingRequestInfo holdingRequest = optionalRequest.get();
            String resourceID = holdingRequest.getItem().getResourceID();
            int amount = holdingRequest.getItem().getAmount();
            wareHouse.getHoldingReques().getRequest().remove(holdingRequest);
            Optional<ItemInfo> optionalItem = wareHouse.getItems().getItem().stream()
                    .filter(item -> item.getResourceID().equals(resourceID))
                    .findFirst();
            if (optionalItem.isPresent()) {
                ItemInfo item = optionalItem.get();
                item.setAmount(item.getAmount() + amount);
            }
           warehouseHandler.saveToXml(); // 保存数据
        }
    }

    /**
     * 提取预留的物品。预留的物品将从仓库中移除
     *
     * @param holdingID 预留请求的ID
     * @return 操作成功返回true，否则返回false
     * @throws InvalidHoldingIDException 如果预留请求ID无效
     */
    @Override
    public boolean pickupHoldingItems(String holdingID) throws InvalidHoldingIDException {
        if (wareHouse.getHoldingReques() == null || wareHouse.getHoldingReques().getRequest() == null) {
            loadWarehouseData();
        }
        Optional<HoldingRequestInfo> optionalRequest = wareHouse.getHoldingReques().getRequest().stream()
                .filter(request -> request.getRequestID().equals(holdingID))
                .findFirst();
        if (optionalRequest.isPresent()) {
            HoldingRequestInfo holdingRequest = optionalRequest.get();
            wareHouse.getHoldingReques().getRequest().remove(holdingRequest);
            warehouseHandler.saveToXml(); // 保存数据
            return true;
        } else {
            throw new InvalidHoldingIDException("Invalid holding request ID");
        }
    }
}
