   package com.easyz.zhfw.controller.v1;

   import com.easyz.zhfw.service.PaymentWS;
   import com.easyz.zhfw.service.impl.PaymentWSImpl;
   import com.easyz.zhfw.vo.TransactionException;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.http.HttpStatus;
   import org.springframework.http.ResponseEntity;
   import org.springframework.web.bind.annotation.*;

   @RestController
   @RequestMapping("/")
   public class PaymentController {

       private final PaymentWS paymentWS;

       @Autowired
       public PaymentController(PaymentWS paymentWS) {
           this.paymentWS = paymentWS;
       }

       /**
        * 查询账户余额
        *
        * @param accountID 账户ID
        * @return 账户余额
        */
       @GetMapping("/queryAccount")
       public ResponseEntity<Integer> queryAccount(@RequestParam String accountID) {
           try {
               int balance = paymentWS.queryAccount(accountID);
               return ResponseEntity.ok(balance);
           } catch (Exception e) {
               return ResponseEntity.badRequest().body(0);
           }
       }

       /**
        * 从账户1向账户2转账
        *
        * @param accountID1 支付方账户ID
        * @param accountID2 受益方账户ID
        * @param amount 转账金额
        * @return 操作成功返回true，否则返回false
        */
       @GetMapping("/transfer")
       public ResponseEntity<Boolean> transfer(@RequestParam String accountID1, @RequestParam String accountID2, @RequestParam int amount) {
           try {
               boolean success = paymentWS.transfer(accountID1, accountID2, amount);
               return ResponseEntity.ok(success);
           } catch (TransactionException e) {
               return ResponseEntity.badRequest().body(false);
           } catch (Exception e) {
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
           }
       }
   }
   