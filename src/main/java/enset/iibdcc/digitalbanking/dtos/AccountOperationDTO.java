package enset.iibdcc.digitalbanking.dtos;

import enset.iibdcc.digitalbanking.models.OperationType;
import java.util.Date;

public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
