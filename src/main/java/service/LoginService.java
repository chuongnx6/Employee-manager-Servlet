package service;

import dto.AccountDto;

public interface LoginService {
    AccountDto checkLogin(String userName, String password);
}
