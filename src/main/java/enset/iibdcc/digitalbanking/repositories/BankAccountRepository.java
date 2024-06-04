package enset.iibdcc.digitalbanking.repositories;

import enset.iibdcc.digitalbanking.models.BankAccountEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {
}
