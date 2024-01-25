package service;

import entity.Account;

public interface AccountService {
    boolean create(Account account);
    Account getById(Integer id);
}
