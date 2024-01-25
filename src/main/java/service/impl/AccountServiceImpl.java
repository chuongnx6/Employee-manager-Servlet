package service.impl;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import entity.Account;
import service.AccountService;
import util.EncryptionUtil;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao = new AccountDaoImpl();
    @Override
    public boolean create(Account account) {
        account.setPassword(EncryptionUtil.encrypt(account.getPassword()));
        return accountDao.create(account) != null;
    }
}
