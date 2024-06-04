package enset.iibdcc.digitalbanking.dtos;

import enset.iibdcc.digitalbanking.models.AccountStatus;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CurrentBankAccountDTO extends BankAccountDTO {
    private double overDraft;

    public CurrentBankAccountDTO(String id, String type, double balance, Date createdAt, AccountStatus status, CustomerDTO customerDTO, double overDraft) {
        super(id, type, balance, createdAt, status, customerDTO);
        this.overDraft = overDraft;
    }
}
