package enset.iibdcc.digitalbanking.dtos;

import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private Optional<UUID> id;
    private String name;
    private String email;
}
