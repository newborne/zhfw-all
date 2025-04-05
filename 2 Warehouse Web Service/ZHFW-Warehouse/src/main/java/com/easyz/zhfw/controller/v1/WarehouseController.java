package com.easyz.zhfw.controller.v1;

import com.easyz.zhfw.service.WarehouseWS;
import com.easyz.zhfw.vo.InvalidHoldingIDException;
import com.easyz.zhfw.vo.NotEnoughItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class WarehouseController {

    @Autowired
    private WarehouseWS warehouseService;

    /**
     * 查询仓库中指定资源ID的物品数量
     *
     * @param resourceID 物品的资源ID
     * @return 仓库中该物品的数量
     */
    @GetMapping("/query")
    public ResponseEntity<Integer> query(@RequestParam String resourceID) {
        int amount = warehouseService.query(resourceID);
        return ResponseEntity.ok(amount);
    }

    /**
     * 从仓库中移除指定数量的物品
     *
     * @param resourceID 物品的资源ID
     * @param amount 需要移除的物品数量
     * @return 操作成功返回true，否则返回false
     */
    @GetMapping("/pickupItems")
    public ResponseEntity<Boolean> pickupItems(@RequestParam String resourceID, @RequestParam int amount) {
        try {
            boolean success = warehouseService.pickupItems(resourceID, amount);
            return ResponseEntity.ok(success);
        } catch (NotEnoughItemException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    /**
     * 补充仓库中指定资源ID的物品数量
     *
     * @param resourceID 物品的资源ID
     * @param amount 需要补充的物品数量
     * @return 补充后仓库中该物品的新数量
     */
    @GetMapping("/complementStock")
    public ResponseEntity<Integer> complementStock(@RequestParam String resourceID, @RequestParam int amount) {
        int newAmount = warehouseService.complementStock(resourceID, amount);
        return ResponseEntity.ok(newAmount);
    }

    /**
     * 预留一些物品以备后续使用。预留的物品在查询操作中不可见，只能通过预留请求ID访问
     *
     * @param resourceID 物品的资源ID
     * @param amount 需要预留的物品数量
     * @return 预留请求的ID（可用于提货或取消预留）
     */
    @GetMapping("/holdItems")
    public ResponseEntity<String> holdItems(@RequestParam String resourceID, @RequestParam int amount) {
        try {
            String holdingID = warehouseService.holdItems(resourceID, amount);
            return ResponseEntity.ok(holdingID);
        } catch (NotEnoughItemException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * 取消预留请求。预留的物品将被释放回仓库
     *
     * @param holdingID 预留请求的ID
     */
    @GetMapping("/cancelHoldingItems")
    public ResponseEntity<Void> cancelHoldingItems(@RequestParam String holdingID) {
        warehouseService.cancelHoldingItems(holdingID);
        return ResponseEntity.ok().build();
    }

    /**
     * 提取预留的物品。预留的物品将从仓库中移除
     *
     * @param holdingID 预留请求的ID
     * @return 操作成功返回true，否则返回false
     */
    @GetMapping("/pickupHoldingItems")
    public ResponseEntity<Boolean> pickupHoldingItems(@RequestParam String holdingID) {
        try {
            boolean success = warehouseService.pickupHoldingItems(holdingID);
            return ResponseEntity.ok(success);
        } catch (InvalidHoldingIDException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
