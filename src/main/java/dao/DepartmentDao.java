package dao;

import entity.Account;

public interface AccountDao extends EntityDao<Account, Integer> {
    @Override
    default Class<Account> getEntityClass() {
        return Account.class;
    }

    Account getByUserName(String userName);
    Account getByEmail(String email);
}
