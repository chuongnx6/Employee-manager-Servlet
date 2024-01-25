package service.impl;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import dto.AccountDto;
import entity.Account;
import service.AccountService;
import util.EncryptionUtil;

import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao = new AccountDaoImpl();
    @Override
    public AccountDto create(AccountDto accountDto) {
        Account account = dtoToEntity(accountDto);
        account.setPassword(EncryptionUtil.encrypt(account.getPassword()));
        return new AccountDto(accountDao.create(account));
    }

    @Override
    public AccountDto getById(Integer id) {
        return new AccountDto(accountDao.getById(id));
    }

    @Override
    public List<AccountDto> getAll() {
        return accountDao.getAll().stream().map(AccountDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean update(AccountDto accountDto) {
        Account account = dtoToEntity(accountDto);
        return accountDao.update(account);
    }

    @Override
    public boolean deleteById(Integer id) {
        return accountDao.deleteById(id);
    }

    private Account dtoToEntity(AccountDto dto) {
        return Account.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .build();
    }
}
