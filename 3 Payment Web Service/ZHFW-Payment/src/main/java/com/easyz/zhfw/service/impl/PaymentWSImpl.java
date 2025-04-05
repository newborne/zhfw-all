package com.easyz.zhfw.service.impl;

import com.easyz.zhfw.handler.PaymentHandler;
import com.easyz.zhfw.pojo.BanKInfo;
import com.easyz.zhfw.pojo.AccountInfo;
import com.easyz.zhfw.service.PaymentWS;
import com.easyz.zhfw.vo.TransactionException;
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
import java.util.stream.Collectors;

@Service
public class PaymentWSImpl implements PaymentWS {

    private final String xmlFilePath = "datasource/ds_2_3.xml"; // XML 文件路径

    private BanKInfo bankInfo;
    private PaymentHandler paymentHandler;

    // 加载银行信息
    private void loadBankInfo() {
        try {
            // 创建SAX解析器
            XMLReader reader = XMLReaderFactory.createXMLReader();
            paymentHandler = new PaymentHandler(xmlFilePath);
            reader.setContentHandler(paymentHandler);
            // 解析XML文件
            reader.parse(new InputSource(new File(xmlFilePath).toURI().toString()));
            bankInfo = paymentHandler.getBank();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
            bankInfo = new BanKInfo(); // 初始化一个空的BanKInfo对象
        }
    }

    public PaymentWSImpl() {
        loadBankInfo();
    }

    /**
     * 查询账户余额
     *
     * @param accountID 账户ID
     * @return 账户余额
     */
    @Override
    public int queryAccount(String accountID) {
        if (bankInfo == null || bankInfo.getAccount() == null) {
            loadBankInfo();
        }
        Optional<AccountInfo> optionalAccount = bankInfo.getAccount().stream()
                .filter(account -> account.getAccountID().equals(accountID))
                .findFirst();
        return optionalAccount.map(AccountInfo::getAmount).orElse(0);
    }

    /**
     * 从账户1向账户2转账
     *
     * @param accountID1 支付方账户ID
     * @param accountID2 受益方账户ID
     * @param amount 转账金额
     * @return 操作成功返回true，否则返回false
     * @throws TransactionException 如果交易失败
     */
    @Override
    public boolean transfer(String accountID1, String accountID2, int amount) throws TransactionException {
        if (bankInfo == null || bankInfo.getAccount() == null) {
            loadBankInfo();
        }

        Optional<AccountInfo> optionalAccount1 = bankInfo.getAccount().stream()
                .filter(account -> account.getAccountID().equals(accountID1))
                .findFirst();

        Optional<AccountInfo> optionalAccount2 = bankInfo.getAccount().stream()
                .filter(account -> account.getAccountID().equals(accountID2))
                .findFirst();

        if (optionalAccount1.isPresent() && optionalAccount2.isPresent()) {
            AccountInfo account1 = optionalAccount1.get();
            AccountInfo account2 = optionalAccount2.get();

            if (account1.getAmount() >= amount) {
                account1.setAmount(account1.getAmount() - amount);
                account2.setAmount(account2.getAmount() + amount);
                paymentHandler.saveToXml(bankInfo); // 保存数据
                return true;
            } else {
                throw new TransactionException("Insufficient balance in account " + accountID1);
            }
        } else {
            throw new TransactionException("One or both accounts do not exist");
        }
    }
}
