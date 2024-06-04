package enset.iibdcc.digitalbanking.dtos;

import enset.iibdcc.digitalbanking.models.AccountStatus;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BankAccountDTO {
    private String id;
    private String type;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
}
