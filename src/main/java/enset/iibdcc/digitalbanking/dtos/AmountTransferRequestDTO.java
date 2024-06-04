package enset.iibdcc.digitalbanking.dtos;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AmountTransferRequestDTO {
    private double amount;
    private UUID accountIdSource;
    private UUID accountIdTarget;
}
