package enset.iibdcc.digitalbanking.dtos;

import enset.iibdcc.digitalbanking.models.AccountStatus;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavingBankAccountDTO extends BankAccountDTO{
    private double interestRate;

    public SavingBankAccountDTO(String id, String type, double balance, Date createdAt, AccountStatus status, CustomerDTO customerDTO, double interestRate) {
        super(id, type, balance, createdAt, status, customerDTO);
        this.interestRate = interestRate;
    }
}
