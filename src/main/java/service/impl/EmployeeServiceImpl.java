package service.impl;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import entity.Account;
import service.AccountService;
import util.EncryptionUtil;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao = new AccountDaoImpl();
    @Override
    public Account create(Account account) {
        account.setPassword(EncryptionUtil.encrypt(account.getPassword()));
        return accountDao.create(account);
    }

    @Override
    public Account getById(Integer id) {
        return accountDao.getById(id);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public boolean update(Account account) {
        return accountDao.update(account);
    }

    @Override
    public boolean deleteById(Integer id) {
        return accountDao.deleteById(id);
    }
}
