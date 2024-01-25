package service.impl;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import entity.Account;
import service.LoginService;
import util.EncryptionUtil;

public class LoginServiceImpl implements LoginService {
    private AccountDao accountDao = new AccountDaoImpl();
    @Override
    public boolean checkLogin(String userName, String password) {
        Account account = accountDao.getByAccount(userName);
        return (account != null && EncryptionUtil.compare(password, account.getPassword()));
    }
}
