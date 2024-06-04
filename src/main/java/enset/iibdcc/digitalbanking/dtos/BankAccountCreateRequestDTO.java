package enset.iibdcc.digitalbanking.dtos;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankAccountCreateRequestDTO {
    private double initialBalance;
    private double overDraft;
    private String type;
    private UUID customerId;
}
