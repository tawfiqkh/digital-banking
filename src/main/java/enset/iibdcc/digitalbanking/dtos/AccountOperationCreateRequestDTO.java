package enset.iibdcc.digitalbanking.dtos;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountOperationCreateRequestDTO{
    private UUID accountId;
    private double amount;
    private String description;
}
