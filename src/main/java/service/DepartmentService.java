package service;

import dto.AccountDto;
import entity.Account;

public interface AccountService extends EntityService<AccountDto, Integer> {
    AccountDto create(AccountDto account);
    AccountDto getById(Integer id);
}
