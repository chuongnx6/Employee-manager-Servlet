package dto;

import entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private int id;
    private String email;
    private String userName;
    private String password;

    public AccountDto(Account account) {
        id = account.getId();
        email = account.getEmail();
        userName = account.getUserName();
    }
}
