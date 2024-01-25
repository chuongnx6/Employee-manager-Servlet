package service.impl;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import dto.AccountDto;
import entity.Account;
import service.LoginService;
import util.EncryptionUtil;

public class LoginServiceImpl implements LoginService {
    private AccountDao accountDao = new AccountDaoImpl();
    @Override
    public AccountDto checkLogin(String userName, String password) {
        AccountDto accountDto = null;
        Account account = accountDao.getByUserName(userName);
        if (account != null && EncryptionUtil.compare(password, account.getPassword())) {
            accountDto = AccountDto.builder()
                    .id(account.getId())
                    .email(account.getEmail())
                    .userName(account.getUserName())
                    .build();
        }
        return accountDto;
    }
}
