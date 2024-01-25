package service;

import dto.AccountDto;

public interface AccountService extends EntityService<AccountDto, Integer> {
    AccountDto create(AccountDto account);
    AccountDto getById(Integer id);
}
